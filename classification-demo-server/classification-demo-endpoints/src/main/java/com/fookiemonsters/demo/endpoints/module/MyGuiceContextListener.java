package com.fookiemonsters.demo.endpoints.module;

import com.fookiemonsters.demo.service.module.MyServiceModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class MyGuiceContextListener extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {

		return Guice.createInjector(new MyEndpointsModule(),
				new MyServiceModule());
	}
}