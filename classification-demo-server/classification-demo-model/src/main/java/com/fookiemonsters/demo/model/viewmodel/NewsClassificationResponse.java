package com.fookiemonsters.demo.model.viewmodel;

public class NewsClassificationResponse {

	private String title;
	private int label;

	public static NewsClassificationResponse createNewsClassificationResponse(
			String title, int label) {

		NewsClassificationResponse res = new NewsClassificationResponse();
		res.setTitle(title);
		res.setLabel(label);
		return res;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public int getLabel() {

		return label;
	}

	public void setLabel(int label) {

		this.label = label;
	}
}
