package com.fookiemonsters.demo.service.exception;

import static java.lang.String.format;

public class NewsRssServiceException extends Exception {

	public static final String ERROR_RETRIEVE_FEED = "error_retrieve_feed";

	public NewsRssServiceException(String code, String detailMessage) {

		super(format("%s: %s", code, detailMessage));
	}

	public NewsRssServiceException(String code, String detailMessage,
			Throwable throwable) {

		super(format("%s: %s", code, detailMessage), throwable);
	}
}