package com.zoho.nl_sql.controller;

import com.zoho.nl_sql.service.ZohoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/zoho")

public class ZohoDataController {

    @Autowired

    private ZohoDataService zohoDataService;

    @GetMapping("/fetch")

    public String fetchData(){

        return zohoDataService.fetchTableData();

    }



}
