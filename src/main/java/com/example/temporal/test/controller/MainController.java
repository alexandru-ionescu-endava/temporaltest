package com.example.temporal.test.controller;

import com.example.temporal.test.workflow.GreetingWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class MainController {

    private final WorkflowClient client;

    @GetMapping("/start/{name}")
    public String start(@PathVariable String name) {
        GreetingWorkflow wf = client.newWorkflowStub(
                GreetingWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("HELLO_TASK_QUEUE")
                        .setWorkflowId("greet-" + name + "-" + UUID.randomUUID())
                        .build()
        );

        WorkflowClient.start(wf::greet, name);

        return WorkflowStub.fromTyped(wf).getExecution().getWorkflowId();
    }

    @GetMapping("/stop/{workflowId}")
    public void stop(@PathVariable String workflowId) {
        GreetingWorkflow wf = client.newWorkflowStub(
                GreetingWorkflow.class,
                workflowId
        );

        wf.stop(); // @SignalMethod stop()
    }

    @GetMapping("/update/{workflowId}/{updatedName}")
    public void update(@PathVariable String workflowId,
                       @PathVariable String updatedName) {

        GreetingWorkflow wf = client.newWorkflowStub(
                GreetingWorkflow.class,
                workflowId // existing workflow
        );

        wf.updateName(updatedName);
    }
}
