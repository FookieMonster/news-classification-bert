package com.fookiemonsters.demo.service;

import com.fookiemonsters.demo.service.module.MyServiceModule;
import com.fookiemonsters.demo.service.module.MyServiceTestModule;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;
import java.util.logging.Logger;

@Guice(modules = { MyServiceModule.class, MyServiceTestModule.class })
public class NewsRssServiceIT {

	@Inject
	private Gson gson;

	@Inject
	private Logger log;

	@Test
	public void testRss() throws Exception {

		FeedFetcher fetcher = new HttpURLFeedFetcher();
		SyndFeed feed = fetcher.retrieveFeed(new URL("https://news.livedoor.com/rss/article/vender/kadench"));
		List<SyndEntry> entries = (List<SyndEntry>) feed.getEntries();

		for (SyndEntry entry : entries) {
			log.info("Name: " + feed.getTitle());
			log.info("Title: " + entry.getTitle());
			log.info("Date: " + entry.getPublishedDate());
			log.info("Url: " + entry.getLink());
		}
	}
}