package com.nemo.detection.interfaces.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author nemo
 */
@Data
@AllArgsConstructor
public class Response implements Serializable {

	private static final String STATUS_OK = "OK";

	private static final String STATUS_ERROR = "ERROR";

	private String code;

	private Object body;

	private String message;

	public static Response ok(Object data) {

		return new Response(STATUS_OK, data, null);
	}

	public static Response error(String message) {

		return new Response(STATUS_ERROR, null, message);
	}
}