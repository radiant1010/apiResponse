package com.example.api_response.apiModule.v2.response;

import com.example.api_response.apiModule.util.ApiResponseContextHolder;
import com.example.api_response.apiModule.v2.interfaces.ApiResponseV2Helper;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice(annotations = ApiResponseV2Helper.class) // 사용할 마커 어노테이션 지정
public class ApiResponseV2Advice implements ResponseBodyAdvice<Object> {

	// Json으로 응답하는 V2 컨트롤러는 모두 적용
	@Override
	public boolean supports(MethodParameter returnType,
		Class<? extends HttpMessageConverter<?>> converterType) {
		return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
	}

	@Override
	public Object beforeBodyWrite(Object body,
		MethodParameter returnType,
		MediaType selectedContentType,
		Class<? extends HttpMessageConverter<?>> selectedConverterType,
		ServerHttpRequest request, ServerHttpResponse response) {

		// 2중 래핑 안되게
		if (body instanceof ApiResponseV2) {
			return body;
		}
		
		// void 타입 리턴일 때
		if (body == null) {
			return ApiResponseV2.ok();
		}

		String msg = ApiResponseContextHolder.getMessage(); // 메시지와 data가 같이 넘어갈때 사용
		ApiResponseContextHolder.clear(); // 사용한 메시지 초기화

		// 메시지, data 성공 응답
		if (msg != null) {
			return ApiResponseV2.ok(body, msg);
		}

		return ApiResponseV2.ok(body); // 성공 값 리턴
	}

}
