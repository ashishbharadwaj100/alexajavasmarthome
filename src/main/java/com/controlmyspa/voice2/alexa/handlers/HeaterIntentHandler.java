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
public class HeaterIntentHandler implements IntentHandler {

	protected Logger logger = LoggerFactory.getLogger(HeaterIntentHandler.class);
	
	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		String slotValue = intent.getSlot("status").getValue();
		Card card = AlexaUtils.newCard("ControlMySpa", "Heaters are "+ slotValue);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech("Heaters are " + slotValue, AlexaUtils.inConversationMode(session));

		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}

}
