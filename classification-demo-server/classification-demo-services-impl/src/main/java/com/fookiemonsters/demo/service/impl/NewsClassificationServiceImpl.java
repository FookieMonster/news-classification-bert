package com.fookiemonsters.demo.service.impl;

import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResponse;
import com.fookiemonsters.demo.service.NewsClassificationService;

import java.io.IOException;
import java.util.List;

public class NewsClassificationServiceImpl implements
		NewsClassificationService {

	@Override
	public NewsClassificationResponse predict(String newsTitle)
			throws IOException {

		return null;
	}

	@Override
	public List<NewsClassificationResponse> predict(List<String> newsTitles)
			throws IOException {

		return null;
	}
}