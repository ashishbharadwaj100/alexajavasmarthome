package com.controlmyspa.voice2.model;

public class RequestPayload {

	String desiredState;
	String originatorId;
	String deviceNumber;
	
	public String getDesiredState() {
		return desiredState;
	}
	public void setDesiredState(String desiredState) {
		this.desiredState = desiredState;
	}
	public String getOriginatorId() {
		return originatorId;
	}
	public void setOriginatorId(String originatorId) {
		this.originatorId = originatorId;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	
	@Override
	public String toString() {
		return "RequestPayload [desiredState=" + desiredState + ", originatorId=" + originatorId + ", deviceNumber="
				+ deviceNumber + "]";
	}
	
	
}
