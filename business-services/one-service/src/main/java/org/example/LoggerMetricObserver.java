package org.example;

import com.netflix.servo.Metric;
import com.netflix.servo.publish.BaseMetricObserver;
import com.netflix.servo.util.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LoggerMetricObserver extends BaseMetricObserver {
    private final Logger logger = LoggerFactory.getLogger(LoggerMetricObserver.class);

    public LoggerMetricObserver(String name) {
        super(name);
    }

    @Override
    public void updateImpl(List<Metric> metrics) {
        Preconditions.checkNotNull(metrics, "metrics");
        for (Metric metric : metrics) {
            logger.info("name[{}] tags[{}] value[{}]", metric.getConfig().getName(), metric.getConfig().getTags(),
                    metric.getValue());
        }
    }
}
