package org.example;

import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.CounterToRateMetricTransform;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TwoServiceApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(TwoServiceApplicationListener.class);

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {

        logger.info("Got event {}", event.getSource());
        initMetricsPublishing();
    }

    private void initMetricsPublishing() {
        PollScheduler pollScheduler = PollScheduler.getInstance();
        if (!pollScheduler.isStarted()) {
            pollScheduler.start();
        }

        MetricObserver logObserver = new LoggerMetricObserver("logger-observer");

        MetricObserver logObserverRate = new LoggerMetricObserver("logger-observer-rate");
        MetricObserver transform = new CounterToRateMetricTransform(logObserverRate, 1, TimeUnit.MINUTES);
        PollRunnable pollRunnable = new PollRunnable(
                new MonitorRegistryMetricPoller(),
                BasicMetricFilter.MATCH_ALL,
                logObserver, transform);
        pollScheduler.addPoller(pollRunnable, 1, TimeUnit.MINUTES);
    }
}
