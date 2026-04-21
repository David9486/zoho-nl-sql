package com.zoho.nl_sql.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service


public class ZohoTokenService {

    @Value("${zoho.client.id}")
    private String clientId;

    @Value("${zoho.client.secret}")
    private String clientSecret;

    @Value("${zoho.refresh.token}")
    private String refreshToken;

    private String accessToken;

    public String getAccessToken(){
        try {
            if (accessToken == null) {

                accessToken = generateNewToken();

            }

            return accessToken;
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public String generateNewToken() throws JSONException {

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://accounts.zoho.in/oauth/v2/token"
                + "?grant_type=refresh_token"
                + "&client_id=" + clientId
                + "&client_secret=" + clientSecret
                + "&refresh_token=" + refreshToken;

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);


        // Parse the access_token from JSON response
        JSONObject json = new JSONObject(response.getBody());
        accessToken = json.getString("access_token");

        System.out.println("✅ New access token generated!");
        return accessToken;

    }

}
