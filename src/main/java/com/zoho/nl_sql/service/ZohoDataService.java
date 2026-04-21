package com.zoho.nl_sql.service;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ZohoDataService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ZohoTokenService zohoTokenService;

    @Value("${zoho.org.id}")
    private String orgId;

    @Value("${zoho.workspace.id}")
    private String workspaceId;

    @Value("${zoho.view.id}")
    private String viewId;

    public String fetchTableData() {
        try {

            return callZohoApi();

        } catch (HttpClientErrorException.Unauthorized e) {

            try {
                System.out.println("Token expired! Regenerating...");
                zohoTokenService.generateNewToken();
                return callZohoApi();
            } catch (JSONException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private String callZohoApi() {
        String url = "https://analyticsapi.zoho.in/restapi/v2/workspaces/"
                + workspaceId + "/views/" + viewId + "/data";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Zoho-oauthtoken " + zohoTokenService.getAccessToken());
        headers.set("ZANALYTICS-ORGID", orgId);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();
    }
}