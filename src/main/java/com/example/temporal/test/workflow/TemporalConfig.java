package com.example.temporal.test.workflow;

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

    public static final String TASK_QUEUE = "hello-task-queue";

    @Bean
    WorkflowServiceStubs service() {
        // connects to localhost:7233 (dev server default)
        return WorkflowServiceStubs
                .newServiceStubs(WorkflowServiceStubsOptions.newBuilder()
                .build());
    }

    @Bean
    WorkflowClient client(WorkflowServiceStubs service) {
        return WorkflowClient.newInstance(service);
    }

    @Bean
    WorkerFactory workerFactory(WorkflowClient client) {
        return WorkerFactory.newInstance(client);
    }

    @Bean(initMethod = "start")
    Worker startWorker(WorkerFactory factory) {
        Worker worker = factory.newWorker(TASK_QUEUE);
        worker.registerWorkflowImplementationTypes(GreetingWorkflowImpl.class);
        worker.registerActivitiesImplementations(new GreetingActivitiesImpl());
        return worker; // factory.start() is called by initMethod on the same bean
    }

    // helper for controllers to create workflow stubs
    public static WorkflowOptions defaultOptions() {
        return WorkflowOptions.newBuilder().setTaskQueue(TASK_QUEUE).build();
    }
}
