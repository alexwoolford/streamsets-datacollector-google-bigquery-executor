package io.woolford.stage.bigquery.executor;


import com.google.cloud.RetryOption;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.BigQuery.JobOption;
import com.streamsets.pipeline.api.Batch;
import com.streamsets.pipeline.api.Stage;
import com.streamsets.pipeline.api.base.BaseExecutor;
import com.streamsets.pipeline.stage.bigquery.lib.BigQueryDelegate;
import com.streamsets.pipeline.stage.bigquery.lib.Errors;
import com.streamsets.pipeline.stage.bigquery.lib.Groups;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BigQueryExecutor extends BaseExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(BigQueryExecutor.class);
    private final BigQueryExecutorConfig conf;
    private BigQuery bigQuery;

    BigQueryExecutor(BigQueryExecutorConfig conf) {
        this.conf = conf;
    }

    public List<ConfigIssue> init() {
        List<ConfigIssue> issues = super.init();
        this.conf.credentials.getCredentialsProvider(this.getContext(), issues).ifPresent((provider) -> {
            if (issues.isEmpty()) {
                try {
                    Optional.ofNullable(provider.getCredentials()).ifPresent((c) -> {
                        this.bigQuery = BigQueryDelegate.getBigquery(c, this.conf.credentials.projectId);
                    });
                } catch (IOException var4) {
                    LOG.error(Errors.BIGQUERY_05.getMessage(), var4);
                    issues.add(((Stage.Context)this.getContext()).createConfigIssue(Groups.CREDENTIALS.name(), "conf.credentials.credentialsProvider", Errors.BIGQUERY_05, new Object[0]));
                }
            }

        });
        return issues;
    }

    public void write(Batch batch) {
        QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(this.conf.query).setUseLegacySql(false).build();
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = this.bigQuery.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build(), new JobOption[0]);

        try {
            queryJob = queryJob.waitFor(new RetryOption[0]);
        } catch (InterruptedException var6) {
            var6.printStackTrace();
        }

        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }
    }
}
