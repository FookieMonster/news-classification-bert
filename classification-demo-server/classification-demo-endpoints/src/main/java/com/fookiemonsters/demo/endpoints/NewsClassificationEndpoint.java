package com.fookiemonsters.demo.endpoints;

import com.fookiemonsters.demo.model.viewmodel.NewsClassificationRequest;
import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResponse;
import com.fookiemonsters.demo.service.NewsClassificationService;
import com.fookiemonsters.demo.service.exception.NewsClassificationServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.inject.Inject;

import java.io.IOException;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;

@Api(name = "newsclassification", version = "v1")
public class NewsClassificationEndpoint {

	@Inject
	private NewsClassificationService service;

	@ApiMethod(name = "classify", path = "classify", httpMethod = POST)
	public NewsClassificationResponse classify(NewsClassificationRequest req)
			throws IOException, NewsClassificationServiceException {

		return service.classify(req.getTitles());
	}
}
