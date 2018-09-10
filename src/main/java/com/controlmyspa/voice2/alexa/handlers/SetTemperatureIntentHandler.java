package com.controlmyspa.voice2.alexa.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;

@Component
public class SetTemperatureIntentHandler implements IntentHandler {

	protected Logger logger = LoggerFactory.getLogger(SetTemperatureIntentHandler.class);

	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		// TODO Auto-generated method stub
		return null;
	}
}
