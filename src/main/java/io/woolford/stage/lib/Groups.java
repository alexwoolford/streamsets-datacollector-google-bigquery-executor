package io.woolford.stage.lib;

import com.streamsets.pipeline.api.GenerateResourceBundle;
import com.streamsets.pipeline.api.Label;

@GenerateResourceBundle
public enum Groups implements Label {
    BIGQUERY("BigQuery"),
    CREDENTIALS("Credentials");

    private final String label;

    private Groups(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}

