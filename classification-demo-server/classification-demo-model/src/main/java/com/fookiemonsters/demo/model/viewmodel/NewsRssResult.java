package com.fookiemonsters.demo.model.viewmodel;

public class NewsRssResult {

	private String siteName;
	private String title;
	private String description;
	private String date;
	private String link;

	public static NewsRssResult createNewsRssResult(String siteName,
			String title, String description, String date, String link) {

		NewsRssResult result = new NewsRssResult();
		result.setSiteName(siteName);
		result.setTitle(title);
		result.setDescription(description);
		result.setDate(date);
		result.setLink(link);
		return result;
	}

	public String getSiteName() {

		return siteName;
	}

	public void setSiteName(String siteName) {

		this.siteName = siteName;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getDate() {

		return date;
	}

	public void setDate(String date) {

		this.date = date;
	}

	public String getLink() {

		return link;
	}

	public void setLink(String link) {

		this.link = link;
	}
}
