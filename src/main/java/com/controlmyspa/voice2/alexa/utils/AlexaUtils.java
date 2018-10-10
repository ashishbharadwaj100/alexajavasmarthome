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
				 											+ " I have sent some information to the Alexa app. Open your Alexa "
				 												+ "app and click on the link to connect your Control My Spa account with Alexa.";
	
	public static final String SamplesHelpText = "Here are some things you can say: Turn lights on or off, what is the set temperature, what is the actual temperature, what is the heater status, set temperature to.";
	public static final String RepromptText = "What else can I tell you?  Say \"Help\" for some suggestions.";
	public static final String userLoginText = "\"Welcome to control my spa. How can i help you?  Say \"Help\" for some suggestions.";
	public static final String gatewayNotConnectedText = "Welcome to control my spa. Your spa is not currently connected to the ControlMySpa cloud. "
															+ "Please ensure your spa is connected to the internet before you can use Alexa to control your spa.";
	public static final String whatCanIDoText = "You can ask me things to help you control your Spa."
			+ "For example, you can ask what is the current water temperature by saying Tell my spa, What is the temperature?"
			+ " Or you can set the desired water temperature by saying ‘Tell my spa, set temperature to XX.’"
			+ " Or you can ask if the heater is On by saying, ‘Tell my spa, is my heater on?’ or"
			+ " you can ask if the lights are On or Off by saying, "
			+ " Tell my spa, are my lights On? and finally you can turn your lights On or Off by saying "
			+ "Tell my spa, turn my lights On.";
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
		//speech.setText( appendRepromptText ? speechText + "\n\n" + AlexaUtils.RepromptText : speechText);
		speech.setText(speechText);
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
