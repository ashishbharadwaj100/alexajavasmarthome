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
public class QueryLightIntentHandler implements IntentHandler {

	protected Logger logger = LoggerFactory.getLogger(QueryLightIntentHandler.class);
	
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
			if(component.getName().equalsIgnoreCase("LIGHT") && component.getPort().equals("0")) {
				status = component.getValue();
				status = status.equalsIgnoreCase("HIGH") ? "ON" : "OFF";
				break;
			}
		}
		
		if(status.length() > 0) {
			Card card = AlexaUtils.newCard("ControlMySpa", "lights Status "+ status);
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("lights are " + status, AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		} else {
			Card card = AlexaUtils.newCard("ControlMySpa", "failed to retrieve light status");
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("Sorry! could not retrieve light status", AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		}
	}

}
