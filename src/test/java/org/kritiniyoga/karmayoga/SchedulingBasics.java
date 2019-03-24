package org.kritiniyoga.karmayoga;

import org.junit.jupiter.api.Test;

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
        TimeSlot slotBeyondDeadline = new TimeSlot(
            deadline.plus(1, ChronoUnit.HOURS),
            deadline.plus(2, ChronoUnit.HOURS));

        assertThrows(IllegalArgumentException.class,
            () -> new Schedule(slotBeyondDeadline, task));
    }

    @Test
    public void givenASlotSmallerThanTask_whenCreatingSchedules_shouldThrowException() {
        Task task = Task.builder().estimate(Duration.ofHours(3)).build();
        Instant now = Instant.now();
        TimeSlot smallerSlot = new TimeSlot(now,
            now.plus(1, ChronoUnit.HOURS));

        assertThrows(IllegalArgumentException.class,
            () -> new Schedule(smallerSlot, task));
    }
}
