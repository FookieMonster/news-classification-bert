package com.fookiemonsters.demo.service.impl;

import com.fookiemonsters.demo.model.viewmodel.NewsRssResponse;
import com.fookiemonsters.demo.model.viewmodel.NewsRssResult;
import com.fookiemonsters.demo.service.NewsRssService;
import com.fookiemonsters.demo.service.exception.NewsRssServiceException;
import com.google.common.collect.Lists;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.fookiemonsters.demo.service.exception.NewsRssServiceException.ERROR_RETRIEVE_FEED;

public class NewsRssServiceImpl implements NewsRssService {

	@Override
	public NewsRssResponse retrieveFeed(String feedUrl)
			throws IOException, NewsRssServiceException {

		FeedFetcher fetcher = new HttpURLFeedFetcher();
		SyndFeed feed = null;

		try {
			feed = fetcher.retrieveFeed(new URL(feedUrl));
		} catch (FeedException | FetcherException e) {
			throw new NewsRssServiceException(ERROR_RETRIEVE_FEED,
					e.getMessage());
		}

		List<SyndEntry> entries = (List<SyndEntry>) feed.getEntries();
		List<NewsRssResult> results = Lists.newArrayList();

		for (SyndEntry entry : entries) {
			results.add(NewsRssResult
					.createNewsRssResult(feed.getTitle(), entry.getTitle(),
							entry.getDescription().getValue(),
							entry.getPublishedDate().toString(),
							entry.getLink()));
		}

		NewsRssResponse response = new NewsRssResponse();
		response.setResults(results);
		return response;
	}
}
