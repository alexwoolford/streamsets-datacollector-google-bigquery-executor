package io.woolford.stage.bigquery.executor;

import com.streamsets.pipeline.api.Config;
import com.streamsets.pipeline.api.StageException;
import com.streamsets.pipeline.api.StageUpgrader;
import com.streamsets.pipeline.api.impl.Utils;
import java.util.List;
import java.util.function.Predicate;

public class BigQueryExecutorUpgrader implements StageUpgrader {
    private static final String BIG_QUERY_TARGET_CONFIG_PREFIX = "conf.";
    private static final String IMPLICIT_FIELD_MAPPING_CONFIG = "conf.implicitFieldMapping";
    private static final String BIG_QUERY_IMPLICIT_FIELD_MAPPING_CONFIG = "conf.bigQueryFieldMappingConfigs";
    private static final String MAX_CACHE_SIZE = "conf.maxCacheSize";

    public BigQueryExecutorUpgrader() {
    }

    public List<Config> upgrade(String library, String stageName, String stageInstance, int fromVersion, int toVersion, List<Config> configs) throws StageException {
        switch(fromVersion) {
            case 1:
                this.upgradeV1toV2(configs);
                return configs;
            default:
                throw new IllegalStateException(Utils.format("Unexpected fromVersion {}", new Object[]{fromVersion}));
        }
    }

    private void upgradeV1toV2(List<Config> configs) {
        configs.removeIf((config) -> {
            return config.getName().equals("conf.implicitFieldMapping") || config.getName().equals("conf.bigQueryFieldMappingConfigs");
        });
        configs.add(new Config("conf.maxCacheSize", -1));
    }
}