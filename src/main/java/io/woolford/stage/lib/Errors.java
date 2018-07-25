package io.woolford.stage.lib;


import com.streamsets.pipeline.api.ErrorCode;
import com.streamsets.pipeline.api.GenerateResourceBundle;

@GenerateResourceBundle
public enum Errors implements ErrorCode {
    BIGQUERY_00("Query Job exceeded timeout."),
    BIGQUERY_02("Query Job execution error: '{}'"),
    BIGQUERY_04("Credentials file '{}' not found"),
    BIGQUERY_05("Error reading credentials file"),
    BIGQUERY_10("Error evaluating expression for the record. Reason : {}"),
    BIGQUERY_11("Error inserting record. Reasons : {}, Messages : {}"),
    BIGQUERY_12("Unsupported field '{}' of type '{}'"),
    BIGQUERY_13("Big Query insert failed. Reason : {}"),
    BIGQUERY_14("Empty row generated for the record"),
    BIGQUERY_15("Error evaluated Row Id, value evaluates to empty"),
    BIGQUERY_16("Root field of record should be a Map or a List Map"),
    BIGQUERY_17("Data set '{}' or Table '{}' does not exist in Big Query under project '{}'");

    private final String msg;

    private Errors(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return this.name();
    }

    public String getMessage() {
        return this.msg;
    }
}

