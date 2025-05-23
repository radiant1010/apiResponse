package com.example.api_response.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@GetMapping("/")
	public ResponseEntity<?> rootController(){
		return ResponseEntity.ok("Index Controller 호출");
	}
}
