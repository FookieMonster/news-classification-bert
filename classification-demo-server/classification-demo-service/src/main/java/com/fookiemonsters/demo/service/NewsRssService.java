package com.fookiemonsters.demo.service;

import com.fookiemonsters.demo.model.viewmodel.NewsRssResponse;
import com.fookiemonsters.demo.service.exception.NewsRssServiceException;

import java.io.IOException;

public interface NewsRssService {

	NewsRssResponse retrieveFeed(String feedUrl)
			throws IOException, NewsRssServiceException;
}
