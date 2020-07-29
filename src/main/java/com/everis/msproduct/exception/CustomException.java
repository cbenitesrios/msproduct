package com.everis.msproduct.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomException extends Exception{ 
	private static final long serialVersionUID = -1146721262268052162L;
	private String message;
	private String code;
	private HttpStatus status;

}
