package com.controlmyspa.voice2.alexa.handlers;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.slu.Intent;
import com.amazon.speech.speechlet.IntentRequest;
import com.amazon.speech.speechlet.LaunchRequest;
import com.amazon.speech.speechlet.Session;
import com.amazon.speech.speechlet.SessionEndedRequest;
import com.amazon.speech.speechlet.SessionStartedRequest;
import com.amazon.speech.speechlet.SpeechletResponse;
import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.ui.Card;
import com.amazon.speech.ui.LinkAccountCard;
import com.amazon.speech.ui.PlainTextOutputSpeech;
import com.controlmyspa.voice2.alexa.utils.AlexaUtils;
import com.controlmyspa.voice2.model.IamUser;
import com.controlmyspa.voice2.model.Spa;
import com.controlmyspa.voice2.services.ControlMySpaApiServices;
import com.controlmyspa.voice2.services.GluuApiServices;


@Service
public class HandlerSpeechlet implements SpeechletV2 {
	protected Logger logger = LoggerFactory.getLogger(HandlerSpeechlet.class);

	@Autowired 
	private BeanFactory beanFactory;
	
	@Autowired
	private GluuApiServices gluuApiServices;
	
	@Autowired
	ControlMySpaApiServices controlMySpaApiServices;
	
	public HandlerSpeechlet() {
	}

	@Override
	public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> requestEnvelope) {
		
		// This is invoked when a new Alexa session is started.  Any initialization logic would go here. 
		// You can store stuff in the Alexa session, for example, by calling:
		// 		Session session = requestEnvelope.getSession();
	}
	
	@Override
	public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> requestEnvelope) {
		
		// This is called when the skill is started with no specific intent.  
		// Such as "Alexa, ask MyDemoApp."
		// When this happens, we should provide a welcome message and prompt 
		// the user to ask a question.
		
		logger.info("onLaunch Called");
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
		logger.info("timestamp={}", requestEnvelope.getRequest().getTimestamp());
		logger.info("locale={}", requestEnvelope.getRequest().getLocale());
		logger.info("AppId={}", requestEnvelope.getSession().getApplication().getApplicationId());
		logger.info("AccessToken={}", requestEnvelope.getSession().getUser().getAccessToken());
		logger.info("UserId={}", requestEnvelope.getSession().getUser().getUserId());
		logger.info("Permissions={}", requestEnvelope.getSession().getUser().getPermissions());

		String accessToken = requestEnvelope.getSession().getUser().getAccessToken();
		Session session = requestEnvelope.getSession();
		
		if(accessToken != null) {
			//validate token with Gluu
			IamUser user = gluuApiServices.validateAccessToken(accessToken);
			logger.info("user={}", user);
			
			// Set a session variable so that we know we're in conversation mode.
			AlexaUtils.setConversationMode(session, true);
			
			//query to find the spa associated with the user
			Spa spa = controlMySpaApiServices.getSpaInfo(user.getUser_name(), accessToken);
			logger.info("spa={}", spa);
			session.setAttribute("spaid", spa.get_id());
			session.setAttribute("user", user.getUser_name());
			
			if(!isSpaConnectedToGateway(spa)) {
				String speechText = "Hello " + user.getGiven_name() +". " + AlexaUtils.gatewayNotConnectedText;
				return sendMessage(speechText, session);
			}
			
			//print the session attributes
			logger.info("sessionid={}", session.getSessionId());
			logger.info("session attributes={}", session.getAttributes());
			
			// Create the initial greeting speech.
			String speechText = "Hello " + user.getGiven_name() +". " + AlexaUtils.userLoginText;
			
			Card card = AlexaUtils.newCard("Welcome!", speechText);
			PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, false);
			
			return AlexaUtils.newSpeechletResponse(card, speech, session, false);
		}
		else {
				LinkAccountCard card = new LinkAccountCard(); card.setTitle("Link your account");
				String speechText = "Hello. " + AlexaUtils.NonUserLinkHelpText;
				PlainTextOutputSpeech speech = AlexaUtils.newSpeech(speechText, false);
				return AlexaUtils.newSpeechletResponse(card, speech, session, false);
		}
		
	}

	@Override	
	public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> requestEnvelope) {
		
		// This is invoked whenever an intent is invoked for our application.  We need
		// to figure out when intent it is, then delegate to a handler for that specific
		// intent.
		logger.info("onIntent Called");
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
		logger.info("timestamp={}", requestEnvelope.getRequest().getTimestamp());
		logger.info("locale={}", requestEnvelope.getRequest().getLocale());
		logger.info("AppId={}", requestEnvelope.getSession().getApplication().getApplicationId());
		logger.info("AccessToken={}", requestEnvelope.getSession().getUser().getAccessToken());
		logger.info("UserId={}", requestEnvelope.getSession().getUser().getUserId());
		logger.info("Permissions={}", requestEnvelope.getSession().getUser().getPermissions());
		
		IntentRequest request = requestEnvelope.getRequest();
		Session session = requestEnvelope.getSession();
		logger.info("request session:" + session.getSessionId() + " "
				+ session.getAttributes());
		
		logger.info("user:" +session.getUser().getAccessToken() + " " + session.getUser().getUserId());
		
		// Get the intent
		Intent intent = request.getIntent();
		if ( intent != null ) {
			// Derive the handler's bean name
			String intentName = intent.getName();
			String handlerBeanName = intentName + "Handler";
			logger.info("intentName={}", intentName);
			// If this is an Amazon Intent, change the handler name to better
			// match up to a Spring bean name.  For example, the intent AMAZON.HelpIntent should
			// be changed to AmazonHelpIntent.
			handlerBeanName = StringUtils.replace(handlerBeanName, "AMAZON.", "Amazon");
			handlerBeanName = handlerBeanName.substring(0, 1).toLowerCase() + handlerBeanName.substring(1);
			
			if ( logger.isInfoEnabled() )
				logger.info("About to invoke handler '" + handlerBeanName + "' for intent '" + intentName + "'.");
			
			// Handle the intent by delegating to the designated handler.
			try {
				Object handlerBean = beanFactory.getBean(handlerBeanName);
			
				if ( handlerBean != null ) {
					
					if ( handlerBean instanceof IntentHandler ) {
						IntentHandler intentHandler = (IntentHandler) handlerBean;
						return intentHandler.handleIntent(intent, request, session);
					}
				}
			}
			catch (Exception e) {
				logger.error("Error handling intent " + intentName, e);
			}
		}
		
		
		// Handle unknown intents.  Ask the user for more info.
		// Start a conversation (if not started already) and say that we did not understand the intent
		AlexaUtils.setConversationMode(session, true);
		
		String errorText = "I don't know what that means. " + "Say help for some suggestions.";
		
		Card card = AlexaUtils.newCard("Dazed and Confused", errorText);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(errorText, false);
		
		return AlexaUtils.newSpeechletResponse(card, speech, session, false);				
	}

	@Override
	public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> requestEnvelope) {
		logger.info("onSessionEnded Called");
		logger.info("onLaunch requestId={}, sessionId={}", requestEnvelope.getRequest().getRequestId(),
				requestEnvelope.getSession().getSessionId());
		logger.info("timestamp={}", requestEnvelope.getRequest().getTimestamp());
		logger.info("locale={}", requestEnvelope.getRequest().getLocale());
		logger.info("AppId={}", requestEnvelope.getSession().getApplication().getApplicationId());
		logger.info("AccessToken={}", requestEnvelope.getSession().getUser().getAccessToken());
		logger.info("UserId={}", requestEnvelope.getSession().getUser().getUserId());
		logger.info("Permissions={}", requestEnvelope.getSession().getUser().getPermissions());
	}

	private boolean isSpaConnectedToGateway(Spa spa) {
		return spa.getCurrentState().isOnline();
	}

	private SpeechletResponse sendMessage(String message, Session session) {
		Card card = AlexaUtils.newCard("ControlMySpa", message);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(message, AlexaUtils.inConversationMode(session));
		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}
}
