package com.example.api_response.apiModule.code;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	BAD_REQUEST("ERR400", "잘못된 요청입니다."),
	NOT_FOUND("ERR404", "요청하신 자원을 찾을 수 없습니다."),
	UNAUTHORIZED("ERR401", "인증이 필요합니다."),
	INTERNAL_SERVER_ERROR("ERR500", "내부 서버 오류가 발생했습니다.");

	private final String code; // 내부적으로 관리할 에러 코드
	private final String errorMessage; // 에러 메시지

	ErrorCodeEnum(String code, String errorMessage) {
		this.code = code;
		this.errorMessage = errorMessage;
	}
}
