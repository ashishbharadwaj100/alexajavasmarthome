package com.controlmyspa.voice2.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.controlmyspa.voice2.model.RequestPayload;
import com.controlmyspa.voice2.model.Spa;
import com.controlmyspa.voice2.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ControlMySpaApiServices {

	protected Logger logger = LoggerFactory.getLogger(ControlMySpaApiServices.class);
	
	public Spa getSpaInfo(String user, String access_token) {
		
		String url = new StringBuilder()
				.append(Constants.BASE_URL)
				.append("/spas/search/findByUsername")
				.toString();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + access_token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<Spa> response = restTemplate.exchange(url, HttpMethod.GET, entity, Spa.class);
		return response.getBody();
	}
	
	public int requestSetControlState(String spaId, String access_token, String control, RequestPayload payload) {
		try {
			String url = new StringBuilder()
					.append(Constants.BASE_URL)
					.append("/control/")
					.append(spaId)
					.append("/set")
					.append(control)
					.append("State")
					.toString();
			logger.info("ControlStateUrl={}", url);
			ObjectMapper objectMapper = new ObjectMapper();
			String requestJson = objectMapper.writeValueAsString(payload);
			
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + access_token);
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
			
			ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
			logger.info("response={}, statusCode={}", response.getBody(), response.getStatusCodeValue());
			return response.getStatusCodeValue();
		}
		catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
