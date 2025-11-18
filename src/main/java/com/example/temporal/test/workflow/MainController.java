package com.example.temporal.test.workflow;

import io.temporal.client.WorkflowClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final WorkflowClient client;

    public MainController(WorkflowClient client) {
        this.client = client;
    }

    @GetMapping("/hello/{name}")
    public String hello(@PathVariable String name) {
        GreetingWorkflow wf = client.newWorkflowStub(GreetingWorkflow.class, TemporalConfig.defaultOptions());
        return wf.greet(name);
    }
}
