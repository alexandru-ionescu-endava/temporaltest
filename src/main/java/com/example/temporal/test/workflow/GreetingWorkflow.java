package com.example.temporal.test.workflow;


import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.UpdateMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface GreetingWorkflow {

    @WorkflowMethod
    String greet(String name);

    // Signal to stop the loop
    @SignalMethod
    void stop();

    @UpdateMethod
    void updateName(String newName);
}
