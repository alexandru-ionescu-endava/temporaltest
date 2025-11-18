package com.example.temporal.test.workflow;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface GreetingActivities {

    String composeGreeting(String name);
}
