package com.fookiemonsters.demo.service;

import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResponse;
import com.fookiemonsters.demo.model.viewmodel.NewsClassificationResult;
import com.fookiemonsters.demo.service.module.MyServiceModule;
import com.fookiemonsters.demo.service.module.MyServiceTestModule;
import com.google.gson.Gson;
import com.google.inject.Inject;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import java.util.List;
import java.util.logging.Logger;

@Guice(modules = { MyServiceModule.class, MyServiceTestModule.class })
public class NewsClassificationServiceIT {

	@Inject
	private NewsClassificationService service;

	@Inject
	private Gson gson;

	@Inject
	private Logger log;

	@Test
	public void test() throws Exception {

		String title1 = "急速充電や大容量バッテリー、エコナビ対応のXi対応ドコモスマホ「ELUGA power P-07D」の気になる価格は？";
		String title2 = "声に反応！　東芝の「VoiPY」で電化製品を声でコントロール【売れ筋チェック】";
		String title3 = "【Sports Watch】朝青龍の協会批判は誤解、通訳者のミスだった!?";

		List<String> titles = Lists.newArrayList(title1, title2, title3);

		NewsClassificationResponse response = service.classify(titles);

		for (NewsClassificationResult result : response.getResults()) {
			log.info(gson.toJson(result));
		}
	}
}
