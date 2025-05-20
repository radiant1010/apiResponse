package com.example.api_response.apiModule.v2.interfaces;

import com.example.api_response.apiModule.v2.response.ApiResponseV2;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.http.ResponseEntity;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponseV2Helper { // 어노테이션명은 정하기 나름
}
