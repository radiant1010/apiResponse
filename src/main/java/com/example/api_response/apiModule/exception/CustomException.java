package com.example.api_response.apiModule.exception;

import com.example.api_response.apiModule.code.ErrorCodeEnum;
import java.io.Serial;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	@Serial
	private static final long serialVersionUID = 8552564411730793576L;

	private String message;
	private boolean redirect;
	private final ErrorCodeEnum errorCode;

	// 에러 메시지 처리가 가능할때 
	public CustomException(String message) {
		super(message);
		this.message = message;
		this.redirect = false;
		this.errorCode = null; 
	}

	// 화면을 리디랙션 처리 해줘야하는 경우
	public CustomException(String message, boolean redirect) {
		super(message);
		this.message = message;
		this.redirect = redirect;
		this.errorCode = ErrorCodeEnum.INTERNAL_SERVER_ERROR;
	}

	// 에러 코드, 메시지를 전닳해줘야 하는 경우
	public CustomException(ErrorCodeEnum errorCode) {
		super(errorCode.getErrorMessage());
		this.message = errorCode.getErrorMessage();
		this.redirect = false;
		this.errorCode = errorCode;
	}

	// 에러메시지가 있으면서 화면 리디랙션이 필요한 경우
	public CustomException(ErrorCodeEnum errorCode, boolean redirect) {
		super(errorCode.getErrorMessage());
		this.message = errorCode.getErrorMessage();
		this.redirect = redirect;
		this.errorCode = errorCode;
	}
}