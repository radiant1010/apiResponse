package com.example.api_response.controller.v2;

import com.example.api_response.apiModule.code.ErrorCodeEnum;
import com.example.api_response.apiModule.exception.CustomException;
import com.example.api_response.apiModule.util.ApiResponseContextHolder;
import com.example.api_response.apiModule.v2.interfaces.ApiResponseV2Helper;
import com.example.api_response.dto.ResponseDTO;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ApiResponseV2Helper
@RequestMapping("/api/v2")
public class RestControllerV2 {

	// 단순 성공 응답
	@GetMapping("/ok")
	public void okDefaultResponseV2() throws Exception {
		log.info("VOID 타입의 리턴입니다.");
	}

	// 성공 메시지 응답
	@GetMapping("/ok/message")
	public String okMessageResponseV2() throws Exception {
		return "정상 처리되었습니다.";
	}

	// data가 있는 경우
	@GetMapping("/ok/data")
	public ResponseDTO okHasDataResponseV2() throws Exception {
		return ResponseDTO.builder()
			.emails(List.of("test1@domain.com", "test2@domain.com"))
			.timeStamp(Timestamp.from(Instant.now()))
			.build();
	}

	@GetMapping("/ok/data-message")
	public ResponseDTO getWithDataAndMessageResponseV2() throws Exception {

		ApiResponseContextHolder.setMessage("데이터 처리 성공");

		return ResponseDTO.builder()
			.emails(List.of("test1@domain.com", "test2@domain.com"))
			.timeStamp(Timestamp.from(Instant.now()))
			.build();
	}

	// 직접 메시지를 내려 주는 에러
	@GetMapping("/error/messageType")
	public String errorMessageTypeResponseV2() throws Exception {
		throw new CustomException("권한이 없습니다.");
	}

	// 에러 코드로 내려 주는 에러
	@GetMapping("/error/codeType")
	public String errorCodeTypeResponseV2() throws Exception {
		throw new CustomException(ErrorCodeEnum.UNAUTHORIZED);
	}

	// 예상하지 못하는 에러 발생 시 Global Exception으로 이동(사용자에게 굳이 보여주지 않아도 되는 메시지)
	@GetMapping("/error/exceptionHandler")
	public String errorExceptionHandlerResponseV2() throws Exception {
		throw new NullPointerException("NPE 에러 발생");
	}
}
