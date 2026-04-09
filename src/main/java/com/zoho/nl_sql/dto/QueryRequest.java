package com.zoho.nl_sql.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data

public class QueryRequest {

    @Getter
    @Setter
    private String query;
    private String type;

}
