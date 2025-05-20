package com.example.api_response.apiModule.v2.response;

import com.example.api_response.apiModule.code.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseV2<T> {

	private static final String SUCCESS_FLAG = "success";
	private static final String ERROR_FLAG = "error";

	/* body 타입 추가로 필요하면 넣기 */

	private String status;
	private T data;
	private String message;
	private String code;

	// 성공 응답
	public static <T> ApiResponseV2<T> ok(T data) {
		return ApiResponseV2.<T>builder()
			.status(SUCCESS_FLAG)
			.data(data)
			.build();
	}

	// 성공 응답 (메시지 없이 성공만 리턴 시,no contents 204로 보내는 방법도 있음)
	public static <T> ApiResponseV2<T> ok() {
		return null;
	}

	// 성공 메시지, 바디 데이터가 있는 경우
	public static <T> ApiResponseV2<T> ok(T data, String message) {
		return ApiResponseV2.<T>builder()
			.status(SUCCESS_FLAG)
			.data(data)
			.message(message)
			.build();
	}

	// 단순 에러 메시지 리턴
	public static <T> ApiResponseV2<T> error(String message) {
		return ApiResponseV2.<T>builder()
			.status(ERROR_FLAG)
			.message(message)
			.build();
	}

	// 에러 코드 응답
	public static <T> ApiResponseV2<T> error(ErrorCodeEnum codeEnum) {
		return ApiResponseV2.<T>builder()
			.status(ERROR_FLAG)
			.code(codeEnum.getCode())
			.message(codeEnum.getErrorMessage())
			.build();
	}

	// 필요 Response 타입 추가
}
