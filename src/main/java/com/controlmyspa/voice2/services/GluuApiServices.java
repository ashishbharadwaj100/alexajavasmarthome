package com.controlmyspa.voice2.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.controlmyspa.voice2.model.IamUser;
import com.controlmyspa.voice2.utils.Constants;

@Service
public class GluuApiServices {

	public IamUser validateAccessToken(String access_token) {
		
		String url = new StringBuilder()
				.append(Constants.IAM_BASE_URL)
				.append("/oxauth/seam/resource/restv1/oxauth/userinfo")
				.toString();
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<IamUser> response = restTemplate.getForEntity(url, IamUser.class);
		return response.getBody();
	}
}
