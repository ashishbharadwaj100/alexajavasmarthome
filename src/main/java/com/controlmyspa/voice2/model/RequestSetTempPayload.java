package com.controlmyspa.voice2.model;

public class RequestSetTempPayload {

	String desiredTemp;

	public String getDesiredTemp() {
		return desiredTemp;
	}

	public void setDesiredTemp(String desiredTemp) {
		this.desiredTemp = desiredTemp;
	}

	@Override
	public String toString() {
		return "RequestSetTempPayload [desiredTemp=" + desiredTemp + "]";
	}
	
	
}
