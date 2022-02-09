package com.catdev.project.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class ErrorResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorType ;
	private String messageEN;
	private String messageVN;
	private String errorCode;

	public ErrorResponse(String errorCode,String errorType, String messageEN, String messageVN) {
		this.errorCode = errorCode;
		this.errorType = errorType;
		this.messageEN = messageEN;
		this.messageVN = messageVN;

	}
	public ErrorResponse(String errorType, String message) {
		super();
		this.errorType = errorType;
		this.messageEN = messageEN;
		this.messageVN = messageVN;
	}

}
