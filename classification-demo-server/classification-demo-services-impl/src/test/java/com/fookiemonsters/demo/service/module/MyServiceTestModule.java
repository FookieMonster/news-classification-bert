package com.fookiemonsters.demo.service.module;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class MyServiceTestModule extends AbstractModule {

	@Override
	protected void configure() {

	}

	@Provides
	public LocalServiceTestHelper provideLocalServiceTestHelper() {

		return new LocalServiceTestHelper(
				new LocalDatastoreServiceTestConfig());
	}
}
