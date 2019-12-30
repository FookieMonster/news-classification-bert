package com.fookiemonsters.demo.model.viewmodel;

import java.util.List;

public class NewsClassificationResponse {

	private List<NewsClassificationResult> results;

	public List<NewsClassificationResult> getResults() {

		return results;
	}

	public void setResults(List<NewsClassificationResult> results) {

		this.results = results;
	}
}
