package com.fookiemonsters.demo.service;

import com.fookiemonsters.demo.service.module.MyServiceModule;
import com.fookiemonsters.demo.service.module.MyServiceTestModule;
import org.testng.annotations.Guice;

@Guice(modules = { MyServiceModule.class, MyServiceTestModule.class })
public class GoogleHttpClientIT {

}
