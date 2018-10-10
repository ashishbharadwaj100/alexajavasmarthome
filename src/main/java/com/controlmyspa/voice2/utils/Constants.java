package com.controlmyspa.voice2.utils;

public class Constants {

	public static final String IAM_BASE_URL = "https://idmqa.controlmyspa.com";
	public static final String BASE_URL = "https://iot.controlmyspa.com/mobile";
	
	
	public static final String TEMP_LOW_CELSIUS = "temperature is too low. "
			+ "please set the temperature between 10 to 40 degree celsius";
	public static final String TEMP_HIGH_CELSIUS = "temperature is too high. "
			+ "please set the temperature between 10 to 40 degree celsius";
	
	public static final String TEMP_LOW_FAHRENHEIT = "temperatue is too low. "
			+ "please set the temperature between 50 to 104 degree fahrenheit.";
	public static final String TEMP_HIGH_FAHRENHEIT = "temperature is too high. "
			+ "please set the temperature between 50 to 104 degree fahrenheit.";
	
	public static final String REQUEST_FAILED = "sorry, i could not fullfill the request due to some internal errors.";
	public static final String REQUEST_FAILED_TEMP_VALUE_INVALID = "sorry, i could not fullfill the request due to invalid temperature value.";
}
