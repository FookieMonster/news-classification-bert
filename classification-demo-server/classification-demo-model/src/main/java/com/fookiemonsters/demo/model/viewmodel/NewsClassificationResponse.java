package com.fookiemonsters.demo.model.viewmodel;

import java.util.List;

public class NewsClassificationResponse {

	private List<String> titles;
	private List<Integer> labels;

	public static NewsClassificationResponse createNewsClassificationResponse(
			List<String> titles, List<Integer> labels) {

		NewsClassificationResponse res = new NewsClassificationResponse();
		res.setTitles(titles);
		res.setLabels(labels);
		return res;
	}

	public List<String> getTitles() {

		return titles;
	}

	public void setTitles(List<String> titles) {

		this.titles = titles;
	}

	public List<Integer> getLabels() {

		return labels;
	}

	public void setLabels(List<Integer> labels) {

		this.labels = labels;
	}
}
