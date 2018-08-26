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
import com.controlmyspa.voice2.model.RequestPayload;
import com.controlmyspa.voice2.services.ControlMySpaApiServices;


@Component
public class LightIntentHandler  implements IntentHandler {

	protected Logger logger = LoggerFactory.getLogger(LightIntentHandler.class);
	
	@Autowired
	ControlMySpaApiServices controlMySpaApiServices;
	
	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		logger.info("sessionid={}", session.getSessionId());
		logger.info("session attributes={}", session.getAttributes());
		
		String slotValue = intent.getSlot("status").getValue();
		
		String desiredState = slotValue.equalsIgnoreCase("on") ? "HIGH" : "OFF";
		
		RequestPayload payload = new RequestPayload();
		payload.setDesiredState(desiredState);
		payload.setDeviceNumber("0");
		payload.setOriginatorId(session.getUser().getAccessToken());
		
		int responseCode = controlMySpaApiServices.requestSetControlState(session.getAttribute("spaid").toString(), 
												session.getUser().getAccessToken(), "Light", payload);
		
		if(responseCode == 202) {
			Card card = AlexaUtils.newCard("ControlMySpa", "lights are "+ slotValue);
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("lights are " + slotValue, AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		}
		else {
			Card card = AlexaUtils.newCard("ControlMySpa", "Could not change light state");
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech("Could not change light state", AlexaUtils.inConversationMode(session));
			return AlexaUtils.newSpeechletResponse( card, speech, session, false);
		}
		
	}

}
