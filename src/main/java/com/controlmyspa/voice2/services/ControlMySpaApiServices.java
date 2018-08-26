package com.controlmyspa.voice2.services;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.controlmyspa.voice2.model.Spa;
import com.controlmyspa.voice2.utils.Constants;

@Service
public class ControlMySpaApiServices {

	public Spa getSpaInfo(String user, String access_token) {
		
		String url = new StringBuilder()
				.append(Constants.BASE_URL)
				.append("/mobile/spas/search/findByUsername")
				.toString();
		
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + access_token);
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
		ResponseEntity<Spa> response = restTemplate.exchange(url, HttpMethod.GET, entity, Spa.class);
		return response.getBody();
	}
	
	
}
