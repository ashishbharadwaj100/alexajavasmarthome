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
import com.controlmyspa.voice2.model.RequestSetTempPayload;
import com.controlmyspa.voice2.model.Spa;
import com.controlmyspa.voice2.services.ControlMySpaApiServices;
import com.controlmyspa.voice2.utils.Constants;

@Component
public class SetTemperatureIntentHandler implements IntentHandler {

	protected Logger logger = LoggerFactory.getLogger(SetTemperatureIntentHandler.class);

	@Autowired
	ControlMySpaApiServices controlMySpaApiServices;
	
	@Override
	public SpeechletResponse handleIntent(Intent intent, IntentRequest request, Session session) {
		logger.info("<<<< SetTemperatureIntentHandler >>>>");
		logger.info("sessionid={}", session.getSessionId());
		logger.info("session attributes={}", session.getAttributes());
		String accessToken = session.getUser().getAccessToken();
		logger.info("access-token={}", accessToken);
		String tempSlotValue = intent.getSlot("temperatureValue").getValue();
		//String scaleSlotValue = intent.getSlot("degree").getValue();
		logger.info("tempSlotValue={}", tempSlotValue);
		
		if(tempSlotValue !=null) {
			float tempValue = Float.parseFloat(tempSlotValue);
			int tempValueRounded = Math.round(tempValue);
			Spa spa = getSpa(session);
			if(spa != null) {
				if(spa.getCurrentState().isCelsius()) {
					if(tempValueRounded < 10) {
						return sendMessage(Constants.TEMP_LOW_CELSIUS, session);
					} else if (tempValueRounded > 40) {
						return sendMessage(Constants.TEMP_HIGH_CELSIUS, session);
					} 
				} 
				
				else {
					if(tempValueRounded < 50) {
						return sendMessage(Constants.TEMP_LOW_FAHRENHEIT, session);
					} else if (tempValueRounded > 104) {
						return sendMessage(Constants.TEMP_HIGH_FAHRENHEIT, session);
					}
				}
				
				RequestSetTempPayload payload = new RequestSetTempPayload();
						payload.setDesiredTemp(String.valueOf(tempValueRounded));
						
				int responseCode = controlMySpaApiServices.requestSetTemp(spa.get_id(), accessToken, payload);
				if(responseCode == 202) {
					String scale = "";
					scale = spa.getCurrentState().isCelsius() ? "celsius" : "fahrenheit";
					String responseMessage = "Your Spa desired water temperature is now " + tempValueRounded + " degrees " + scale;
					return sendMessage(responseMessage, session);
				} else {
					return sendMessage(Constants.REQUEST_FAILED, session);
				}
				
			} else {
				return sendMessage(Constants.REQUEST_FAILED, session);
			}
			
		} else {
			return sendMessage(Constants.REQUEST_FAILED_TEMP_VALUE_INVALID, session);
		}
		
		
		/*if(tempSlotValue !=null && scaleSlotValue != null) {
			float tempValue = Float.parseFloat(tempSlotValue);
			int tempValueRounded = Math.round(tempValue);
			if(scaleSlotValue.equalsIgnoreCase("celsius")) {
				if(tempValueRounded < 10) {
					return sendMessage(Constants.TEMP_LOW_CELSIUS, session);
				} else if (tempValueRounded > 40) {
					return sendMessage(Constants.TEMP_HIGH_CELSIUS, session);
				} else {
					Spa spa = getSpa(session);
					if(spa != null) {
						RequestSetTempPayload payload = new RequestSetTempPayload();
						if(spa.getCurrentState().isCelsius()) {
							payload.setDesiredTemp(String.valueOf(tempValueRounded));
						} else {
							int tempInFloat = Math.round(convertCelsiusToFahrenheit(tempValue));
							payload.setDesiredTemp(String.valueOf(tempInFloat));
						}
					  controlMySpaApiServices.requestSetTemp(spa.get_id(), accessToken, payload);
					} else {
						sendMessage(Constants.REQUEST_FAILED, session);
					}
				}
			} 
			else {
				if(tempValueRounded < 50) {
					return sendMessage(Constants.TEMP_LOW_FAHRENHEIT, session);
				} else if (tempValueRounded > 104) {
					return sendMessage(Constants.TEMP_HIGH_FAHRENHEIT, session);
				} else {
					Spa spa = getSpa(session);
					if(spa != null) {
						RequestSetTempPayload payload = new RequestSetTempPayload();
						if(spa.getCurrentState().isCelsius()) {
							int tempInFloat =  Math.round(convertFahrenheitToCelsius(tempValue));
								payload.setDesiredTemp(String.valueOf(tempInFloat));
						} else {
							payload.setDesiredTemp(String.valueOf(tempValueRounded));
						}
					  controlMySpaApiServices.requestSetTemp(spa.get_id(), accessToken, payload);
					} else {
						sendMessage(Constants.REQUEST_FAILED, session);
					}
					
				}
			}
		} else if (tempSlotValue !=null && scaleSlotValue == null) {
			float tempValue = Float.parseFloat(tempSlotValue);
			int tempValueRounded = Math.round(tempValue);
			Spa spa = getSpa(session);
			if(spa != null) {
				if(spa.getCurrentState().isCelsius()) {
					if(tempValueRounded < 10) {
						return sendMessage(Constants.TEMP_LOW_CELSIUS, session);
					} else if (tempValueRounded > 50) {
						return sendMessage(Constants.TEMP_HIGH_CELSIUS, session);
					} 
				} 
				
				else {
					if(tempValueRounded < 50) {
						return sendMessage(Constants.TEMP_LOW_FAHRENHEIT, session);
					} else if (tempValueRounded > 104) {
						return sendMessage(Constants.TEMP_HIGH_FAHRENHEIT, session);
					}
				}
				
				RequestSetTempPayload payload = new RequestSetTempPayload();
						payload.setDesiredTemp(String.valueOf(tempValueRounded));
						
				int responseCode = controlMySpaApiServices.requestSetTemp(spa.get_id(), accessToken, payload);
				if(responseCode == 202) {
					String scale = "";
					scale = spa.getCurrentState().isCelsius() ? "celsius" : "fahrenheit";
					String responseMessage = "Your Spa desired water temperature is now " + tempValueRounded + " degrees " + scale;
					return sendMessage(responseMessage, session);
				} else {
					sendMessage(Constants.REQUEST_FAILED, session);
				}
				
			} else {
				sendMessage(Constants.REQUEST_FAILED, session);
			}
			
		}*/
		
	}
	
	private float convertCelsiusToFahrenheit(float cel) {
		/* Convert Celsius to Fahrenheit */
        float fahren =  cel * (9f / 5) + 32;
        return fahren;
	}
	
	private float convertFahrenheitToCelsius(float fahr) {
		float cel = ((fahr - 32)*5)/9;
		return cel;
	}
	
	private Spa getSpa(Session session) {
		Spa spa = controlMySpaApiServices.getSpaInfo(session.getAttribute("user").toString(),
				session.getUser().getAccessToken());
		logger.info("spa={}", spa);
		return spa;
	}
	
	private SpeechletResponse sendMessage(String message, Session session) {
		Card card = AlexaUtils.newCard("ControlMySpa", message);
		PlainTextOutputSpeech speech = AlexaUtils.newSpeech(message, AlexaUtils.inConversationMode(session));
		return AlexaUtils.newSpeechletResponse( card, speech, session, false);
	}
}
