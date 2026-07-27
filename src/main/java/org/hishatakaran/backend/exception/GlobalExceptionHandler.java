package org.hishatakaran.backend.exception;

import java.util.HashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {


	@ExceptionHandler(SomethingWentWrongException.class)
	@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<ApiError> somethingWentWrongException(HttpServletRequest req, SomethingWentWrongException e) {
		return buildResponse(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), req.getRequestURI());
	}

	private ResponseEntity<ApiError> buildResponse(HttpStatus httpStatus, String message, String uri) {
		var errors = new HashMap<String, String>();
		errors.put("message", message);
		var apiError = new ApiError(httpStatus.value(), uri, errors);
		return ResponseEntity.status(httpStatus).body(apiError);
	}
}