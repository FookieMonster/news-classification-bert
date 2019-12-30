package com.fookiemonsters.demo.service.module;

import com.fookiemonsters.demo.service.NewsClassificationService;
import com.fookiemonsters.demo.service.NewsRssService;
import com.fookiemonsters.demo.service.impl.NewsClassificationServiceImpl;
import com.fookiemonsters.demo.service.impl.NewsRssServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import java.util.ResourceBundle;

public class MyServiceModule extends AbstractModule {

	@Override
	protected void configure() {

		bind(NewsClassificationService.class).toInstance(new NewsClassificationServiceImpl());
		bind(NewsRssService.class).toInstance(new NewsRssServiceImpl());
	}

	@Provides
	public ResourceBundle provideResourceBundle() {

		return ResourceBundle.getBundle("service");
	}

	@Provides
	public Gson provideGson() {

		return new GsonBuilder().setPrettyPrinting().create();
	}
}