package com.sports.depthchart.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice

@RestController
public class SportsDepthChartExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(GenericExceptions.class)
	public final ResponseEntity<Object> handleAllExceptions
	(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse exceptionResponse	= 
				new ExceptionResponse(ex.getMessage(),request.getDescription(false),new Date());
		return new ResponseEntity(exceptionResponse,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException
	(Exception ex, WebRequest request) throws Exception {
		ExceptionResponse exceptionResponse	= 
				new ExceptionResponse(ex.getMessage(),request.getDescription(false),new Date());
		return new ResponseEntity(exceptionResponse,HttpStatus.NOT_FOUND);
	}
}
