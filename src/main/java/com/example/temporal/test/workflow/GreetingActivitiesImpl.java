package com.example.temporal.test.workflow;

public class GreetingActivitiesImpl
        implements GreetingActivities {

    @Override
    public String composeGreeting(String name) {
        return "Hello, " + name + "! Welcome to Temporal";
    }
}
