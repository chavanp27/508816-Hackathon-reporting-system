package com.cts.ora.report.exception;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class ORAException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Throwable rootCauseException = null;
	private String errorCode;
	private String exceptionMsg;
	
	public ORAException(String errorCode, String exceptionMsg,Throwable throwable) {
		super(errorCode, throwable);
		this.errorCode = errorCode;
		this.exceptionMsg = exceptionMsg;
	}
	
}
