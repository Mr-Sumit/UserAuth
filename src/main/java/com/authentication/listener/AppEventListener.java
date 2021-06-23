package com.authentication.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
@Configuration
public class AppEventListener implements ApplicationListener{

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
		Class eventClass = event.getClass();
		System.out.println(eventClass.toString());
	}

}
