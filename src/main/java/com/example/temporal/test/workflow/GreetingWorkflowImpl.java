package com.example.temporal.test.workflow;

import com.example.temporal.test.activities.GreetingActivities;
import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;

import java.time.Duration;

public class GreetingWorkflowImpl
        implements GreetingWorkflow {

    private final GreetingActivities activities =
            Workflow.newActivityStub(GreetingActivities.class,
                    ActivityOptions.newBuilder()
                            .setStartToCloseTimeout(Duration.ofSeconds(10))
                            .build());

    private boolean isRunning = true;
    private String greetingName;

    @Override
    public String greet(String name) {
        this.greetingName = name;

        while (isRunning) {
            activities.composeGreeting(this.greetingName);
            Workflow.sleep(Duration.ofSeconds(5));
        }

        return "ok";
    }

    @Override
    public void stop() {
        isRunning = false;
    }

    @Override
    public void updateName(String newName) {
        this.greetingName = newName;
    }
}
