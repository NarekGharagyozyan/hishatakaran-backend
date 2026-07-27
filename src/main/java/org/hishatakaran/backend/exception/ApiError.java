package org.hishatakaran.backend.exception;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {

	private Integer status;
	private String path;
	private Map<String, String> errors;

	public ApiError(Integer status, String path, Map<String, String> errors) {
		super();
		this.status = status;
		this.path = path;
		this.errors = errors;
	}

}