package com.controlmyspa.voice2.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ComponentState {

	private String componentType;
    private String materialType;
    private String port;
    private String serialNumber;
    private String value;
    private String targetValue;
    private List<String> availableValues;
    private String name;
    private Date registeredTimestamp;
    private String componentId;
    
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getTargetValue() {
		return targetValue;
	}
	public void setTargetValue(String targetValue) {
		this.targetValue = targetValue;
	}
	public List<String> getAvailableValues() {
		return availableValues;
	}
	public void setAvailableValues(List<String> availableValues) {
		this.availableValues = availableValues;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getRegisteredTimestamp() {
		return registeredTimestamp;
	}
	public void setRegisteredTimestamp(Date registeredTimestamp) {
		this.registeredTimestamp = registeredTimestamp;
	}
	public String getComponentId() {
		return componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	
	@Override
	public String toString() {
		return "ComponentState [componentType=" + componentType + ", materialType=" + materialType + ", port=" + port
				+ ", serialNumber=" + serialNumber + ", value=" + value + ", targetValue=" + targetValue
				+ ", availableValues=" + availableValues + ", name=" + name + ", registeredTimestamp="
				+ registeredTimestamp + ", componentId=" + componentId + "]";
	}
    
    
    
}
