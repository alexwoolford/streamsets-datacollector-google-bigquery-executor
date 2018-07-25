package io.woolford.stage.bigquery.executor;



import com.streamsets.pipeline.api.ConfigDefBean;
import com.streamsets.pipeline.api.ConfigGroups;
import com.streamsets.pipeline.api.Executor;
import com.streamsets.pipeline.api.StageDef;
import com.streamsets.pipeline.api.base.configurablestage.DExecutor;
import com.streamsets.pipeline.stage.bigquery.lib.Groups;

@StageDef(
        version = 1,
        label = "Google BigQuery",
        description = "Executes Google BigQuery statements",
        icon = "bigquery.png",
        producesEvents = true,
        upgrader = BigQueryExecutorUpgrader.class,
        onlineHelpRefUrl = "index.html?contextID=task_gxn_dsk_dbb"
)
@ConfigGroups(Groups.class)
public class BigQueryDExecutor extends DExecutor {
    @ConfigDefBean
    public BigQueryExecutorConfig conf;

    public BigQueryDExecutor() {
    }

    protected Executor createExecutor() {
        return new BigQueryExecutor(this.conf);
    }
}
