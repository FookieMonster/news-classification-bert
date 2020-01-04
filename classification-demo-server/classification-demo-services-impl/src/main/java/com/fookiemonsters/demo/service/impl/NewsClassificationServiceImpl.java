package com.fookiemonsters.demo.service.impl;

import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResponse;
import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResult;
import com.fookiemonsters.demo.service.NewsClassificationService;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.*;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.appengine.api.utils.SystemProperty;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.inject.Inject;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import static com.google.api.client.http.ByteArrayContent.fromString;

public class NewsClassificationServiceImpl
		implements NewsClassificationService {

	private static final String HTTP_METHOD = "POST";
	private static final String HTTP_CONTENT_TYPE = "application/x-jsonlines";

	@Inject
	private ResourceBundle bundle;

	@Override
	public NewsClassificationResponse classify(List<String> newsTitles)
			throws IOException {

		String proxyServerUrl = bundle.getString("proxy_server_url");

		String jsonLines = toJsonLines(newsTitles);

		HttpTransport transport = isAppEngine() ? new UrlFetchTransport() : new ApacheHttpTransport();
		HttpRequestFactory factory = transport.createRequestFactory();

		HttpContent content = fromString(HTTP_CONTENT_TYPE, jsonLines);
		GenericUrl url = new GenericUrl(proxyServerUrl);
		HttpRequest request = factory.buildRequest(HTTP_METHOD, url, content);

		HttpResponse response = request.execute();
		String responseBody = response.parseAsString();
		List<String> lines = fromJsonLines(responseBody);

		return makeResponse(newsTitles, lines);
	}

	private boolean isAppEngine() {

		return SystemProperty.environment.value() == SystemProperty.Environment.Value.Production;
	}

	private NewsClassificationResponse makeResponse(List<String> titles,
			List<String> lines) {

		List<NewsClassificationResult> results = Lists.newArrayList();

		for (int i = 0; i < titles.size(); i++) {

			String title = titles.get(i);
			String jsonl = lines.get(i);
			List<Float> predictions = parserPrediction(jsonl);

			results.add(NewsClassificationResult
					.createNewsClassificationResult(title, argmax(predictions),
							predictions));
		}

		NewsClassificationResponse response = new NewsClassificationResponse();
		response.setResults(results);
		return response;
	}

	private String toJsonLines(List<String> titles) {

		StringBuilder sb = new StringBuilder();
		for (String title : titles) {
			sb.append(String.format("{\"text\": \"%s\"}\n", title));
		}
		return sb.toString();
	}

	private List<String> fromJsonLines(String jsonLines) {

		String[] lines = jsonLines.split("\n");
		return Lists.newArrayList(lines);
	}

	private List<Float> parserPrediction(String jsonl) {

		JsonParser parser = new JsonParser();
		JsonObject predObj = parser.parse(jsonl).getAsJsonObject();
		JsonArray predArray = predObj.getAsJsonArray("prediction");
		return arrayToList(predArray);
	}

	private List<Float> arrayToList(JsonArray array) {

		List<Float> results = Lists.newArrayList();

		for (int i = 0; i < array.size(); i++) {
			results.add(array.get(i).getAsFloat());
		}
		return results;
	}

	private int argmax(List<Float> predictions) {

		int maxIndex = 0;
		float maxValue = predictions.get(0);

		for (int i = 0; i < predictions.size(); i++) {

			if (maxValue < predictions.get(i)) {
				maxValue = predictions.get(i);
				maxIndex = i;
			}
		}
		return maxIndex;
	}
}