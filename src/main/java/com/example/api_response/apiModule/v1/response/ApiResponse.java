package com.example.api_response.apiModule.v1.response;

import com.example.api_response.apiModule.code.ErrorCodeEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {

	private static final String SUCCESS = "success";
	private static final String ERROR = "error";

	private String status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String message;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String code;

	public static <T> ApiResponse<T> ok() {
		return ApiResponse.<T>builder()
			.status(SUCCESS)
			.data(null)
			.build();
	}

	public static <T> ApiResponse<T> ok(T data) {
		return ApiResponse.<T>builder()
			.status(SUCCESS)
			.data(data)
			.build();
	}

	public static <T> ApiResponse<T> error(String message) {
		return ApiResponse.<T>builder()
			.status(ERROR)
			.message(message)
			.build();
	}

	// 에러 코드 필요시 리턴
	public static <T> ApiResponse<T> error(ErrorCodeEnum codeEnum) {
		return ApiResponse.<T>builder()
			.status(ERROR)
			.message(codeEnum.getErrorMessage())
			.code(codeEnum.getCode())
			.build();
	}

}
