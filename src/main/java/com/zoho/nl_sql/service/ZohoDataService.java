package com.zoho.nl_sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ZohoDataService {

@Autowired

    private RestTemplate restTemplate;

    @Value("${zoho.access.token}")
    private String accessToken;

    @Value("${zoho.org.id}")
    private String orgId;

    @Value("${zoho.workspace.id}")
    private String workspaceId;

    @Value("${zoho.view.id}")
    private String viewId;

    public String fetchTableData(){

        String url = "https://analyticsapi.zoho.in/restapi/v2/workspaces/"
                + workspaceId + "/views/" + viewId + "/data";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Zoho-oauthtoken " + accessToken);
        headers.set("ZANALYTICS-ORGID", orgId);

        //wrapping headers and body together as one object
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //exchange makes actual http call
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );

        return response.getBody();

    }

}
