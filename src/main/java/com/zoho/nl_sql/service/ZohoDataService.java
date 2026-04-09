package com.zoho.nl_sql.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String ACCESS_TOKEN = "1000.6ee1fdfdd67bf05b8a8c423f301ca98c.3dfc5725865dae5158728da08939a5a6";
    private static final String ORG_ID = "60067461398";
    private static final String WORKSPACE_ID = "526255000000002004";
    private static final String VIEW_ID = "526255000000004002";

    public String fetchTableData(){

        String url = "https://analyticsapi.zoho.in/restapi/v2/workspaces/"
                + WORKSPACE_ID + "/views/" + VIEW_ID + "/data";

        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Zoho-oauthtoken " + ACCESS_TOKEN);
        headers.set("ZANALYTICS-ORGID", ORG_ID);

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
