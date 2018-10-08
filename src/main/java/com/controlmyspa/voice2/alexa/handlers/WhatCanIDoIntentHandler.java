package com.controlmyspa.voice2.alexa.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.controlmyspa.voice2.alexa.utils.AlexaUtils;


@Component
public class WhatCanIDoIntentHandler implements IntentHandler {
	
	protected Logger logger = LoggerFactory.getLogger(WhatCanIDoIntentHandler.class);
	
	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		logger.info("sessionid={}", session.getSessionId());
		logger.info("session attributes={}", session.getAttributes());
		Card card = AlexaUtils.newCard("ControlMySpa", AlexaUtils.whatCanIDoText);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(AlexaUtils.whatCanIDoText, AlexaUtils.inConversationMode(session));
		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}

}
