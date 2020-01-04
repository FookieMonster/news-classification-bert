package com.fookiemonsters.demo.model.viewmodel;

import java.util.List;

public class NewsClassificationResult {

	private String title;
	private int label;
	private List<Float> predictions;

	public static NewsClassificationResult createNewsClassificationResult(
			String title, int label, List<Float> predictions) {

		NewsClassificationResult result = new NewsClassificationResult();
		result.setTitle(title);
		result.setLabel(label);
		result.setPredictions(predictions);
		return result;
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

	public List<Float> getPredictions() {

		return predictions;
	}

	public void setPredictions(List<Float> predictions) {

		this.predictions = predictions;
	}
}
