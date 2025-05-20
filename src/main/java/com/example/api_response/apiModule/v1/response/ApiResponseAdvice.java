package com.example.api_response.apiModule.v1.response;

import com.example.api_response.apiModule.exception.CustomException;
import com.example.api_response.apiModule.v2.interfaces.ApiResponseV2Helper;
import com.example.api_response.apiModule.v2.response.ApiResponseV2;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
@Slf4j
public class ApiResponseAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType,
		Class<? extends HttpMessageConverter<?>> converterType) {

		Class<?> controllerClass = returnType.getDeclaringClass();

		// V2는 처리 안하도록 
		if (controllerClass.isAnnotationPresent(ApiResponseV2Helper.class)) {
			return false;
		}

		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);

	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType,
		MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
		ServerHttpResponse response) {

		// 파일(Resource) 다운로드인 경우, 그대로 반환하여 ResponseEntity 처리 로직을 사용하게 함
		if (body instanceof ResponseEntity
			&& ((ResponseEntity<?>) body).getBody() instanceof Resource) {
			return body;
		}
		
		// 만약에 V2이면 바디 그대로 넘겨주기(v2에서 처리 하도록)
		if (body instanceof ApiResponseV2) {
			return body;
		}

		if (body instanceof CustomException) {
			return ApiResponse.error(((CustomException) body).getMessage());
		} else if (body instanceof MethodArgumentNotValidException) {
			return ApiResponse.error(((MethodArgumentNotValidException) body).getMessage());
		} else {
			return ApiResponse.ok(body);
		}
	}
}
