package com.fookiemonsters.demo.endpoints.module;

import com.fookiemonsters.demo.endpoints.NewsClassificationEndpoint;
import com.google.api.control.ServiceManagementConfigFilter;
import com.google.api.control.extensions.appengine.GoogleAppEngineControlFilter;
import com.google.api.server.spi.guice.EndpointsModule;
import com.google.common.collect.ImmutableList;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MyEndpointsModule extends EndpointsModule {

	@Override
	public void configureServlets() {

		super.configureServlets();

		ResourceBundle bundle = ResourceBundle.getBundle("endpoints");

		bind(ServiceManagementConfigFilter.class).in(Singleton.class);
		filter("/_ah/api/*").through(ServiceManagementConfigFilter.class);

		Map<String, String> apiController = new HashMap<>();
		apiController.put("endpoints.projectId", bundle.getString("appengine_project_id"));
		apiController.put("endpoints.serviceName", bundle.getString("appengine_server_name"));

		bind(GoogleAppEngineControlFilter.class).in(Singleton.class);
		filter("/_ah/api/*").through(GoogleAppEngineControlFilter.class, apiController);

		bind(NewsClassificationEndpoint.class).toInstance(new NewsClassificationEndpoint());

		configureEndpoints("/_ah/api/*",
				ImmutableList.of(NewsClassificationEndpoint.class));
	}
}