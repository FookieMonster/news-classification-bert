package com.fookiemonsters.demo.service.exception;

import static java.lang.String.format;

public class NewsClassificationServiceException extends Exception {

	public NewsClassificationServiceException(String code, String detailMessage) {

		super(format("%s: %s", code, detailMessage));
	}

	public NewsClassificationServiceException(String code, String detailMessage,
			Throwable throwable) {

		super(format("%s: %s", code, detailMessage), throwable);
	}
}
