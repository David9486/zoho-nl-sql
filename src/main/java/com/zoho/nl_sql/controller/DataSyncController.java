package com.zoho.nl_sql.controller;

import com.zoho.nl_sql.service.DataSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sync")
public class DataSyncController {

    @Autowired
    private DataSyncService dataSyncService;

    // Why GET mapping?
    // → Simple trigger endpoint
    // → Hit this URL → sync starts automatically
    @GetMapping
    public String sync() {
        return dataSyncService.syncData();
    }
}