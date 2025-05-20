package com.example.api_response.apiModule.exception;

import com.example.api_response.apiModule.code.ErrorCodeEnum;
import com.example.api_response.apiModule.v1.response.ApiResponse;
import com.example.api_response.apiModule.v2.interfaces.ApiResponseV2Helper;
import jakarta.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Order(2) // v2 이후에 처리되도록 우선순위 2로 지정
@ControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

	@ExceptionHandler({CustomException.class,
		NoResourceFoundException.class,
		SQLSyntaxErrorException.class,
		Exception.class})
	public Object handleGeneralExceptions(Exception ex, HttpServletRequest request, Model model) {
		log.error("[Global Exception]: {}", ex.getMessage(), ex);
		
		// v2에서 넘어오는 에러 안잡기
		Object handler = request.getAttribute(HandlerMapping.BEST_MATCHING_HANDLER_ATTRIBUTE);
		if (handler instanceof HandlerMethod method) {
			Class<?> controllerClass = method.getBeanType();
			if (controllerClass.isAnnotationPresent(ApiResponseV2Helper.class)) {
				log.debug("[handleGeneralExceptions V1] V2 컨트롤러 API로 무시");
				return null;
			}
		}

		// 기본 상태 및 메시지
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorCodeEnum codeEnum = ErrorCodeEnum.INTERNAL_SERVER_ERROR;
		String errorMsg = ex.getMessage();

		if (ex instanceof CustomException) {
			status = HttpStatus.BAD_REQUEST;
			errorMsg = ex.getMessage();
		} else if (ex instanceof NoResourceFoundException) {
			status = HttpStatus.NOT_FOUND;
			codeEnum = ErrorCodeEnum.NOT_FOUND;
			errorMsg = codeEnum.getErrorMessage();
		} else if (hasSqlError(ex)) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			codeEnum = ErrorCodeEnum.INTERNAL_SERVER_ERROR;
			errorMsg = codeEnum.getErrorMessage();
		} else {

			if (errorMsg == null || errorMsg.isBlank()) {
				errorMsg = codeEnum.getErrorMessage();
			}
		}

		// API 요청일 경우 JSON 응답
		if (isApiRequest(request)) {
			if (ex instanceof CustomException) {
				return ResponseEntity.status(status).body(ApiResponse.error(errorMsg));
			} else {
				return ResponseEntity.status(status).body(ApiResponse.error(codeEnum));
			}
		}

		// 일반 요청일 경우 에러 페이지로
		request.getSession().setAttribute("errorMsg", errorMsg);
		request.getSession().setAttribute("errorCode", codeEnum.getCode());
		request.getSession().setAttribute("statusCode", status.value());
		return "redirect:/error";
	}

	// API 요청인지 체크
	private boolean isApiRequest(HttpServletRequest request) {
		String accept = request.getHeader("Accept");
		String requestedWith = request.getHeader("X-Requested-With");
		String uri = request.getRequestURI();

		return (accept != null && accept.contains("application/json"))
			|| (requestedWith != null && "XMLHttpRequest".equals(requestedWith));
	}


	private boolean hasSqlError(Throwable ex) {
		while (ex != null) {
			if (ex instanceof SQLException
				|| ex.getClass().getName().toLowerCase().contains("sql")) {
				return true;
			}
			ex = ex.getCause();
		}
		return false;
	}
}