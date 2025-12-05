package com.example.temporal.test.config;

import com.example.temporal.test.activities.GreetingActivitiesImpl;
import com.example.temporal.test.workflow.GreetingWorkflowImpl;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TemporalConfig {

    public static final String TASK_QUEUE = "HELLO_TASK_QUEUE";

    @Bean
    WorkflowServiceStubs service() {
        // localhost:7233 by default
        return WorkflowServiceStubs.newServiceStubs(
                WorkflowServiceStubsOptions.newBuilder().build()
        );
    }

    @Bean
    WorkflowClient client(WorkflowServiceStubs service) {
        return WorkflowClient.newInstance(service);
    }

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    WorkerFactory workerFactory(WorkflowClient client) {
        WorkerFactory factory = WorkerFactory.newInstance(client);

        Worker worker = factory.newWorker(TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetingActivitiesImpl());

        return factory;
    }

    public static WorkflowOptions defaultOptions() {
        return WorkflowOptions.newBuilder()
                .setTaskQueue(TASK_QUEUE)
                .build();
    }
}
