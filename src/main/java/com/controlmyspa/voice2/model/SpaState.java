package com.controlmyspa.voice2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpaState {

	// required props of all spas
    String runMode; // rest, ready
    String desiredTemp;
    String targetDesiredTemp;
    String currentTemp;
    String controllerType;
    int errorCode;
    boolean cleanupCycle;
    Date uplinkTimestamp = null;
    Date staleTimestamp = null;
    String heaterMode;
    int hour;
    int minute;
    boolean online;
    boolean celsius;
    boolean demoMode;
    boolean timeNotSet;
    boolean settingsLock;
    boolean spaOverheatDisabled;
    String bluetoothStatus;
    int updateIntervalSeconds;
    int wifiUpdateIntervalSeconds;
    List<ComponentState> components;
    
    public SpaState(){
        components = new ArrayList<ComponentState>();
    }

	public String getRunMode() {
		return runMode;
	}

	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}

	public String getDesiredTemp() {
		return desiredTemp;
	}

	public void setDesiredTemp(String desiredTemp) {
		this.desiredTemp = desiredTemp;
	}

	public String getTargetDesiredTemp() {
		return targetDesiredTemp;
	}

	public void setTargetDesiredTemp(String targetDesiredTemp) {
		this.targetDesiredTemp = targetDesiredTemp;
	}

	public String getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}

	public String getControllerType() {
		return controllerType;
	}

	public void setControllerType(String controllerType) {
		this.controllerType = controllerType;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public boolean isCleanupCycle() {
		return cleanupCycle;
	}

	public void setCleanupCycle(boolean cleanupCycle) {
		this.cleanupCycle = cleanupCycle;
	}

	public Date getUplinkTimestamp() {
		return uplinkTimestamp;
	}

	public void setUplinkTimestamp(Date uplinkTimestamp) {
		this.uplinkTimestamp = uplinkTimestamp;
	}

	public Date getStaleTimestamp() {
		return staleTimestamp;
	}

	public void setStaleTimestamp(Date staleTimestamp) {
		this.staleTimestamp = staleTimestamp;
	}

	public String getHeaterMode() {
		return heaterMode;
	}

	public void setHeaterMode(String heaterMode) {
		this.heaterMode = heaterMode;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public boolean isCelsius() {
		return celsius;
	}

	public void setCelsius(boolean celsius) {
		this.celsius = celsius;
	}

	public boolean isDemoMode() {
		return demoMode;
	}

	public void setDemoMode(boolean demoMode) {
		this.demoMode = demoMode;
	}

	public boolean isTimeNotSet() {
		return timeNotSet;
	}

	public void setTimeNotSet(boolean timeNotSet) {
		this.timeNotSet = timeNotSet;
	}

	public boolean isSettingsLock() {
		return settingsLock;
	}

	public void setSettingsLock(boolean settingsLock) {
		this.settingsLock = settingsLock;
	}

	public boolean isSpaOverheatDisabled() {
		return spaOverheatDisabled;
	}

	public void setSpaOverheatDisabled(boolean spaOverheatDisabled) {
		this.spaOverheatDisabled = spaOverheatDisabled;
	}

	public String getBluetoothStatus() {
		return bluetoothStatus;
	}

	public void setBluetoothStatus(String bluetoothStatus) {
		this.bluetoothStatus = bluetoothStatus;
	}

	public int getUpdateIntervalSeconds() {
		return updateIntervalSeconds;
	}

	public void setUpdateIntervalSeconds(int updateIntervalSeconds) {
		this.updateIntervalSeconds = updateIntervalSeconds;
	}

	public int getWifiUpdateIntervalSeconds() {
		return wifiUpdateIntervalSeconds;
	}

	public void setWifiUpdateIntervalSeconds(int wifiUpdateIntervalSeconds) {
		this.wifiUpdateIntervalSeconds = wifiUpdateIntervalSeconds;
	}

	public List<ComponentState> getComponents() {
		return components;
	}

	public void setComponents(List<ComponentState> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "SpaState [runMode=" + runMode + ", desiredTemp=" + desiredTemp + ", targetDesiredTemp="
				+ targetDesiredTemp + ", currentTemp=" + currentTemp + ", controllerType=" + controllerType
				+ ", errorCode=" + errorCode + ", cleanupCycle=" + cleanupCycle + ", uplinkTimestamp=" + uplinkTimestamp
				+ ", staleTimestamp=" + staleTimestamp + ", heaterMode=" + heaterMode + ", hour=" + hour + ", minute="
				+ minute + ", online=" + online + ", celsius=" + celsius + ", demoMode=" + demoMode + ", timeNotSet="
				+ timeNotSet + ", settingsLock=" + settingsLock + ", spaOverheatDisabled=" + spaOverheatDisabled
				+ ", bluetoothStatus=" + bluetoothStatus + ", updateIntervalSeconds=" + updateIntervalSeconds
				+ ", wifiUpdateIntervalSeconds=" + wifiUpdateIntervalSeconds + ", components=" + components + "]";
	}

}
