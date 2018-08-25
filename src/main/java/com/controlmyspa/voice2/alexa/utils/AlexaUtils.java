package com.controlmyspa.voice2.alexa.utils;

import java.util.Random;

import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.amazon.speech.ui.Reprompt;
import com.amazon.speech.ui.StandardCard;

public final class AlexaUtils {
	protected static final String SESSION_CONVERSATION_FLAG = "conversation";
	
	public static final String NonUserLinkHelpText = "Welcome to Control My Spa. To use this skill, you must have a Control My Spa account." 
				 											+ "I've sent some information to the Alexa app. Open your Alexa "
				 												+ "app and click on the link to connect your Control My Spa account with Alexa.";
	
	public static final String SamplesHelpText = "\"Welcome to control my spa: Here are some things you can say: Turn lights on or turn lights off, Turn blower on or off";
	public static final String RepromptText = "What else can I tell you?  Say \"Help\" for some suggestions.";
	
	private AlexaUtils() {
	}
	
	
	public static Card newCard( String cardTitle, String cardText ) {
		
		StandardCard card = new StandardCard();
		card.setTitle( (cardTitle == null) ? "MyDemoApp" : cardTitle );
		card.setText(cardText);

		/*
		Image cardImage = new Image();
		cardImage.setSmallImageUrl("https://www.cutlerstew.com/static/images/cutlerstew-720x480.png");
		cardImage.setLargeImageUrl("https://www.cutlerstew.com/static/images/cutlerstew-1200x800.png");
		
		card.setImage(cardImage);
		*/

		return card;
	}
	
	public static PlainTextOutputSpeech newSpeech( String speechText, boolean appendRepromptText ) {
		
		PlainTextOutputSpeech speech = new PlainTextOutputSpeech();
		speech.setText( appendRepromptText ? speechText + "\n\n" + AlexaUtils.RepromptText : speechText);

		return speech;
	}
	
	public static SpeechletResponse newSpeechletResponse(Card card, PlainTextOutputSpeech speech, Session session, boolean shouldEndSession)  {
		
		// Say it...
		if ( AlexaUtils.inConversationMode(session) && !shouldEndSession) {
			PlainTextOutputSpeech repromptSpeech = new PlainTextOutputSpeech();
			repromptSpeech.setText(AlexaUtils.RepromptText);
			
			Reprompt reprompt = new Reprompt();
			reprompt.setOutputSpeech(repromptSpeech);
			
			return SpeechletResponse.newAskResponse(speech, reprompt, card);
		}
		else {		
			return SpeechletResponse.newTellResponse(speech, card);
		}
	}

	
	public static void setConversationMode(Session session, boolean conversationMode) {		
		if ( conversationMode )
			session.setAttribute(SESSION_CONVERSATION_FLAG, "true");
		else
			session.removeAttribute(SESSION_CONVERSATION_FLAG);
	}

	public static boolean inConversationMode(Session session) {
		 return session.getAttribute(SESSION_CONVERSATION_FLAG) != null;
	}	
	
	public static int randomInt(int min, int max) 
	{
		Random r = new Random( System.currentTimeMillis() );
		return r.nextInt((max - min) + 1) + min;
	}

}
