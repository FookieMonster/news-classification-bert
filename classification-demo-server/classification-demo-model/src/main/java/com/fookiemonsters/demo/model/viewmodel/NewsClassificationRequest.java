package com.fookiemonsters.demo.model.viewmodel;

public class NewsClassificationRequest {

	private String title;

	public static NewsClassificationRequest createNewsClassificationRequest(
			String title) {

		NewsClassificationRequest req = new NewsClassificationRequest();
		req.setTitle(title);
		return req;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}
}
