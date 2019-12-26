package com.fookiemonsters.demo.service.impl;

import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResponse;
import com.fookiemonsters.demo.service.NewsClassificationService;
import com.fookiemonsters.demo.service.exception.NewsClassificationServiceException;

import java.io.IOException;
import java.util.List;

public class NewsClassificationServiceImpl
		implements NewsClassificationService {

	@Override
	public NewsClassificationResponse classify(List<String> newsTitles)
			throws IOException, NewsClassificationServiceException {

		return null;
	}
}