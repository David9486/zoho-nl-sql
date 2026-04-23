package com.zoho.nl_sql.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataSyncService {

    @Autowired

    private JdbcTemplate jdbcTemplate;

    @Autowired

    private ZohoDataService zohoDataService;

    public String syncData() {

        String rawData = zohoDataService.fetchTableData();
        System.out.println("Raw data from Zoho: " + rawData);


        String[] lines = rawData.split("\n");

        //First line is always the header (column names)
        //e.g. "id,name,city"
        String[] columns = lines[0].split(",");

        System.out.println("Columns found: " + columns.length);
        for (String col : columns) {
            System.out.println("  → " + col.trim());
        }

        // Create table dynamically
        createTable("customers", columns);

        // Insert rows
        // Start from index 1 because index 0 is the header
        int rowsInserted = 0;
        for (int i = 1; i < lines.length; i++) {
            if (!lines[i].trim().isEmpty()) {
                String[] values = lines[i].split(",");
                insertRow("customers", columns, values);
                rowsInserted++;
            }
        }

        return "Sync complete! " + rowsInserted + " rows inserted.";

    }

    private void createTable(String tableName, String[] columns) {

        // Why DROP TABLE IF EXISTS?
        // → Every time we sync we recreate the table fresh
        // → Avoids duplicate data issues
        String dropSql = "DROP TABLE IF EXISTS " + tableName;
        jdbcTemplate.execute(dropSql);
        System.out.println("Dropped existing table: " + tableName);

        // Build CREATE TABLE SQL dynamically
        // All columns are TEXT type for simplicity
        StringBuilder createSql = new StringBuilder();
        createSql.append("CREATE TABLE ").append(tableName).append(" (");

        for (int i = 0; i < columns.length; i++) {
            createSql.append(columns[i].trim()).append(" TEXT");
            if (i < columns.length - 1) {
                createSql.append(", ");
            }
        }
        createSql.append(")");

        System.out.println("Creating table with SQL: " + createSql);
        jdbcTemplate.execute(createSql.toString());
        System.out.println("Table created: " + tableName);

    }

    private void insertRow(String tableName, String[] columns, String[] values) {

        // Build INSERT SQL dynamically
        // e.g. INSERT INTO customers (id, name, city) VALUES (?, ?, ?)
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("INSERT INTO ").append(tableName).append(" (");

        // Add column names
        for (int i = 0; i < columns.length; i++) {
            insertSql.append(columns[i].trim());
            if (i < columns.length - 1) insertSql.append(", ");
        }

        insertSql.append(") VALUES (");

        // Add ? placeholders for values
        // Why ? placeholders?
        // → Prevents SQL injection attacks
        // → Spring fills them in safely
        for (int i = 0; i < values.length; i++) {
            insertSql.append("?");
            if (i < values.length - 1) insertSql.append(", ");
        }
        insertSql.append(")");

        // Trim whitespace from values
        Object[] trimmedValues = new Object[values.length];
        for (int i = 0; i < values.length; i++) {
            trimmedValues[i] = values[i].trim();
        }

        jdbcTemplate.update(insertSql.toString(), trimmedValues);
        System.out.println("Row inserted: " + String.join(", ", values));
    }

}
