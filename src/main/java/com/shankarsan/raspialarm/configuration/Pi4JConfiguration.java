package com.shankarsan.raspialarm.configuration;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pi4j.Pi4J;
import com.pi4j.context.Context;

@Configuration
public class Pi4JConfiguration {
	
	private static Logger _log = LoggerFactory.getLogger(Pi4JConfiguration.class);
	
	private Context context;
	
	@Bean(name = "Pi4JContext")
	public Context getContext() {
		context = Pi4J.newAutoContext();
		return context;
	}
	
	@PreDestroy
	public void onExit() {
		_log.info("Shutting down Pi4JContext.");
		context.shutdown();
	}

}
