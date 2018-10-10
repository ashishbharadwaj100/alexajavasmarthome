package com.controlmyspa.voice2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IamUser {

	String user_name;
	String sub;
	String email;
	String inum;
	String name;
	String family_name;
	String given_name;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getInum() {
		return inum;
	}
	public void setInum(String inum) {
		this.inum = inum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	
	@Override
	public String toString() {
		return "IamUser [user_name=" + user_name + ", sub=" + sub + ", email=" + email + ", inum=" + inum + ", name="
				+ name + ", family_name=" + family_name + ", given_name=" + given_name + "]";
	}
	
}
