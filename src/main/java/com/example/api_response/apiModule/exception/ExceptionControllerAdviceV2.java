package com.example.api_response.apiModule.exception;

import com.example.api_response.apiModule.code.ErrorCodeEnum;
import com.example.api_response.apiModule.v2.interfaces.ApiResponseV2Helper;
import com.example.api_response.apiModule.v2.response.ApiResponseV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@Slf4j
@RestControllerAdvice(annotations = ApiResponseV2Helper.class) // API V2만 진입하도록 설정
public class ExceptionControllerAdviceV2 {

	// 예외 처리가 가능한 오류들은 200으로 리턴
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponseV2<Void>> handleCustomException(CustomException ex) {
		log.error("[Custom Exception V2]", ex);

		if (ex.getErrorCode() != null) {
			return ResponseEntity.ok(ApiResponseV2.error(ex.getErrorCode()));
		} else {
			return ResponseEntity.ok(ApiResponseV2.error(ex.getMessage()));
		}
	}

	// NPE, SQL, 예상하지 못하는 시스템 에러 발생시 여기로 진입 -> fetchUtil에서 !response.ok로 잡아서 reject 처리
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponseV2<Void>> handleGlobalException(Exception ex) {
		log.error("[Global Exception V2]", ex);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(ApiResponseV2.error(ErrorCodeEnum.INTERNAL_SERVER_ERROR));
	}
}
