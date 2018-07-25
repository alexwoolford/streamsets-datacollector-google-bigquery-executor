package io.woolford.stage.bigquery.executor;

import com.streamsets.pipeline.api.ConfigDef;
import com.streamsets.pipeline.api.FieldSelectorModel;
import com.streamsets.pipeline.api.ConfigDef.Type;

public class BigQueryFieldMappingConfig {
    @ConfigDef(
            required = true,
            type = Type.STRING,
            defaultValue = "",
            label = "Column",
            description = "The column to write this field into. Column must exist",
            displayPosition = 40
    )
    public String columnName;
    @ConfigDef(
            required = true,
            type = Type.MODEL,
            defaultValue = "",
            label = "Field Path For Column",
            description = "The field path in the incoming record to map to column",
            displayPosition = 50
    )
    @FieldSelectorModel(
            singleValued = true
    )
    public String fieldPath;

    public BigQueryFieldMappingConfig() {
    }
}
