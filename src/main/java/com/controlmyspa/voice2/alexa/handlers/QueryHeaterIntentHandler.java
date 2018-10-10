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
import com.controlmyspa.voice2.model.ComponentState;
import com.controlmyspa.voice2.model.Spa;
import com.controlmyspa.voice2.services.ControlMySpaApiServices;

@Component
public class QueryHeaterIntentHandler implements IntentHandler {

	protected Logger logger = LoggerFactory.getLogger(QueryHeaterIntentHandler.class);
	
	@Autowired
	ControlMySpaApiServices controlMySpaApiServices;
	
	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		String status = "";
		logger.info("sessionid={}", session.getSessionId());
		logger.info("session attributes={}", session.getAttributes());
		Spa spa = controlMySpaApiServices.getSpaInfo(session.getAttribute("user").toString(),
								session.getUser().getAccessToken());
		logger.info("spa={}", spa);
		
		for(ComponentState component : spa.getCurrentState().getComponents()) {
			if(component.getName().equalsIgnoreCase("HEATER")) {
				status = component.getValue();
				break;
			}
		}
		
		if(status.length() > 0) {
			Card card = AlexaUtils.newCard("ControlMySpa", "heater is "+ status);
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("heater is " + status, AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		} else {
			Card card = AlexaUtils.newCard("ControlMySpa", "failed to retrieve heater status");
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("Sorry! could not retrieve heater status", AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		}
	}

}
