package org.kritiniyoga.karmayoga;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TimeSlotBasics {

    private Instant now, future;


    @BeforeEach
    public void setup() {
        now = Instant.now();
        future = now.plus(2,
            ChronoUnit.HOURS);
    }

    @Test
    public void shouldBeAbleToCreateASlot() {
        TimeSlot timeSlot = new TimeSlot(now, future);
        assertNotNull(timeSlot);
    }

    @Test
    public void shouldThrowExceptionWhenEndIsBeforeFuture() {

        Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> new TimeSlot(future, now));

    }

}
