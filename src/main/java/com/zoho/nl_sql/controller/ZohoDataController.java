package com.zoho.nl_sql.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/query")

public class QueryController {

    @Autowired

    private QueryService queryService;



}
