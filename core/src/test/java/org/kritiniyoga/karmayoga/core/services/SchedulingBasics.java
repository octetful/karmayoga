package org.kritiniyoga.karmayoga.core.services;

import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.core.entities.Schedule;
import org.kritiniyoga.karmayoga.core.entities.Task;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SchedulingBasics {
    @Test
    public void givenASlotAfterDeadline_whenCreatingSchedules_shouldThrowException() {
        Instant deadline = Instant.now();
        Task task = Task.builder()
            .deadline(
                Date.from(deadline))
            .build();
        TimeSlot slotBeyondDeadline = TimeSlot.createFrom(
            deadline.plus(1, ChronoUnit.HOURS),
            deadline.plus(2, ChronoUnit.HOURS));

        assertThrows(IllegalArgumentException.class,
            () -> Schedule.createFromOrFail(slotBeyondDeadline, task));
    }

    @Test
    public void givenASlotSmallerThanTask_whenCreatingSchedules_shouldThrowException() {
        Task task = Task.builder().estimate(Duration.ofHours(3)).build();
        Instant now = Instant.now();
        TimeSlot smallerSlot = TimeSlot.createFrom(now,
            now.plus(1, ChronoUnit.HOURS));

        assertThrows(IllegalArgumentException.class,
            () -> Schedule.createFromOrFail(smallerSlot, task));
    }
}
