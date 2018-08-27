package com.controlmyspa.voice2.alexa.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.controlmyspa.voice2.alexa.utils.AlexaUtils;
import com.controlmyspa.voice2.model.Spa;
import com.controlmyspa.voice2.services.ControlMySpaApiServices;

@Component
public class QueryTempIntentHandler implements IntentHandler {

protected Logger logger = LoggerFactory.getLogger(QueryTempIntentHandler.class);
	
	@Autowired
	ControlMySpaApiServices controlMySpaApiServices;
	
	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		String scale = "";
		logger.info("sessionid={}", session.getSessionId());
		logger.info("session attributes={}", session.getAttributes());
		
		Spa spa = controlMySpaApiServices.getSpaInfo(session.getAttribute("user").toString(),
								session.getUser().getAccessToken());
		logger.info("spa={}", spa);
		
		if(spa != null) {
			scale = spa.getCurrentState().isCelsius() ? "celsius" : "fahrenheit";
			
			String temp = spa.getCurrentState().getCurrentTemp();
			
			if(scale.equals("celsius")) {
				int tempFa = Integer.valueOf(temp);
				int celsius = (int) Math.round((tempFa-32)*(0.5556));
				temp = String.valueOf(celsius);
			}
			
			Card card = AlexaUtils.newCard("ControlMySpa", "Current Temperature is " + temp + " " + scale);
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("Current Temperature is " + temp + " " + scale, AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		}
		else {
			Card card = AlexaUtils.newCard("ControlMySpa", "sorry!, could not retrieve current temperature");
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("sorry!, could not retrieve current temperature", AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		}

	}

}
