package com.example.temporal.test.activities;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface GreetingActivities {

    String composeGreeting(String name);
}
