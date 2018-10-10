package com.controlmyspa.voice2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Spa {

	private String _id;
    private String serialNumber;
    private String productName;
    private String model;
    
    private SpaState currentState;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public SpaState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(SpaState currentState) {
		this.currentState = currentState;
	}

	@Override
	public String toString() {
		return "Spa [_id=" + _id + ", serialNumber=" + serialNumber + ", productName=" + productName + ", model="
				+ model + ", currentState=" + currentState + "]";
	}
    
 
}
