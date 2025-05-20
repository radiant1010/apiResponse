package com.example.api_response.dto;

import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseDTO {
	private List<String> emails;
	private Timestamp timeStamp;
}
