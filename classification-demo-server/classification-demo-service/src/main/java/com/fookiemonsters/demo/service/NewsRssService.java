package com.fookiemonsters.demo.service;

import com.fookiemonsters.demo.model.viewmodel.NewsRssResponse;

public interface NewsRssService {

	NewsRssResponse retrieveFeed(String feedUrl);
}
