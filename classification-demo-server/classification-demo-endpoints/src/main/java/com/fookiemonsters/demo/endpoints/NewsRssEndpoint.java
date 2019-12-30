package com.fookiemonsters.demo.endpoints;

import com.fookiemonsters.demo.model.viewmodel.NewsRssRequest;
import com.fookiemonsters.demo.model.viewmodel.NewsRssResponse;
import com.fookiemonsters.demo.service.NewsRssService;
import com.fookiemonsters.demo.service.exception.NewsRssServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.inject.Inject;

import java.io.IOException;

import static com.google.api.server.spi.config.ApiMethod.HttpMethod.POST;

@Api(name = "newsrss", version = "v1")
public class NewsRssEndpoint {

	@Inject
	private NewsRssService service;

	@ApiMethod(name = "feed", path = "feed", httpMethod = POST)
	public NewsRssResponse retrieveFeed(NewsRssRequest req)
			throws IOException, NewsRssServiceException {

		return service.retrieveFeed(req.getFeedUrl());
	}
}