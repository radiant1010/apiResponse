package com.example.api_response.apiModule.util;


// 성공응답에 메시지를 담아서 객체와 같이 보내는 경우 사용
public class ApiResponseContextHolder {

	private static final ThreadLocal<String> messageHolder = new ThreadLocal<>();

	public static void setMessage(String message) {
		messageHolder.set(message);
	}

	public static String getMessage() {
		return messageHolder.get();
	}

	public static void clear() {
		messageHolder.remove();
	}
}