package org.kritiniyoga.karmayoga.core.domain.factories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.core.domain.values.TimeSlot;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeSlotCreation {
    private Instant now, future;
    private TimeSlot timeSlot;


    @BeforeEach
    public void setup() {
        now = Instant.now();
        future = now.plus(2,
                ChronoUnit.HOURS);
        timeSlot = TimeSlot.createFrom(now, future);
    }

    @Test
    public void withStartAndEnd_shouldCreateTimeSlot() {
        assertNotNull(timeSlot);
    }

    @Test
    public void whenEndIsBeforeFuture_shouldThrowException() {
        assertThrows( NoSuchElementException.class,
                () -> TimeSlot.createFrom(future, now));
    }

}
