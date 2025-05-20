package com.example.api_response.controller.v1;

import com.example.api_response.apiModule.exception.CustomException;
import com.example.api_response.dto.ResponseDTO;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Slf4j
@Controller
@RequestMapping("/api/v1")
public class RestControllerV1 {
	
	// 보통 정상적인 리턴
	@GetMapping("/ok/data")
	@ResponseBody
	public ResponseDTO okHasDataResponse() throws Exception {
		return ResponseDTO.builder()
			.emails(List.of("test1@domain.com", "test2@domain.com"))
			.timeStamp(Timestamp.from(Instant.now()))
			.build();
	}
	
	// CustomException 발생으로 에러 컨트롤
	@GetMapping("/error/messageType")
	public String errorMessageTypeResponse() throws Exception {
		throw new CustomException("권한이 없습니다.");
	}
	
	// 예상하지 못한 에러 발생
	@GetMapping("/error/exceptionHandler")
	public String errorExceptionHandlerResponse() throws Exception {
		throw new NullPointerException("NPE 에러 발생");
	}
}
