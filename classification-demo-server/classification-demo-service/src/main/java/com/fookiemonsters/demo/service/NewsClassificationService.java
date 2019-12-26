package com.fookiemonsters.demo.service;

import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResponse;

import java.io.IOException;
import java.util.List;

public interface NewsClassificationService {

	NewsClassificationResponse predict(String newsTitle) throws IOException;

	List<NewsClassificationResponse> predict(List<String> newsTitles) throws IOException;
}
