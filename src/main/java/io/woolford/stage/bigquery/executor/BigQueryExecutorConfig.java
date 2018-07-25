package io.woolford.stage.bigquery.executor;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.ConfigDefBean;
import com.streamsets.pipeline.api.ConfigDef.Evaluation;
import com.streamsets.pipeline.api.ConfigDef.Type;
import com.streamsets.pipeline.stage.lib.GoogleCloudCredentialsConfig;

public class BigQueryExecutorConfig {
    @ConfigDef(
            required = true,
            label = "Query",
            type = ConfigDef.Type.TEXT,
            mode = ConfigDef.Mode.SQL,
            defaultValue = "",
            description = "Google BigQuery statement to execute",
            displayPosition = 60,
            group = "BIGQUERY",
            evaluation = Evaluation.EXPLICIT
    )
    public String query;
    @ConfigDefBean(
            groups = {"CREDENTIALS"}
    )
    public GoogleCloudCredentialsConfig credentials = new GoogleCloudCredentialsConfig();

    public BigQueryExecutorConfig() {
    }
}