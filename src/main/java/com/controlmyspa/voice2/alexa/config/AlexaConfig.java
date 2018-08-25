package com.controlmyspa.voice2.alexa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazon.speech.json.SpeechletRequestModule;
import com.amazon.speech.speechlet.servlet.SpeechletServlet;
import com.controlmyspa.voice2.alexa.handlers.HandlerSpeechlet;
import com.fasterxml.jackson.databind.Module;



@Configuration
public class AlexaConfig {

	@Autowired
	private HandlerSpeechlet handlerSpeechlet;
	
	@Bean
	public ServletRegistrationBean registerSpeechletServlet() {
		
		SpeechletServlet speechletServlet = new SpeechletServlet();
		speechletServlet.setSpeechlet(handlerSpeechlet);
		
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(speechletServlet, "/alexa");
		return servletRegistrationBean;
	}
	
	@Bean
	Module speechletRequestModule() {
	    return new SpeechletRequestModule();
	}

}

