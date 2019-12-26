package com.fookiemonsters.demo.model.viewmodel;

import java.util.List;

public class NewsClassificationRequest {

	private List<String> titles;

	public static NewsClassificationRequest createNewsClassificationRequest(
			List<String> titles) {

		NewsClassificationRequest req = new NewsClassificationRequest();
		req.setTitles(titles);
		return req;
	}

	public List<String> getTitles() {

		return titles;
	}

	public void setTitles(List<String> titles) {

		this.titles = titles;
	}
}
