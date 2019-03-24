package org.kritiniyoga.karmayoga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TaskBasics {
    private Task task;

    @BeforeEach
    public void setup() {
        task = Task.builder().build();
    }

    @Test
    public void shouldBeAbleToCreateTasks() {
        assertNotNull(task);
    }

}
