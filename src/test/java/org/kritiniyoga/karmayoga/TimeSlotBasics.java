package org.kritiniyoga.karmayoga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeSlotBasics {

    private Instant now, future;
    private TimeSlot timeSlot;
    private Instant halfSplit;


    @BeforeEach
    public void setup() {
        now = Instant.now();
        future = now.plus(2,
            ChronoUnit.HOURS);
        timeSlot = new TimeSlot(now, future);
        halfSplit = now.plus(1, ChronoUnit.HOURS);
    }

    @Test
    public void withStartAndEnd_shouldCreateTimeSlot() {
        assertNotNull(timeSlot);
    }

    @Test
    public void whenEndIsBeforeFuture_shouldThrowException() {
        assertThrows( IllegalArgumentException.class,
            () -> new TimeSlot(future, now));
    }

    @Test
    public void givenAnInstanceWithin_shouldBeAbleToSpitASlot() {
        List<TimeSlot> splitSlots = timeSlot.split(halfSplit);
        assertThat(splitSlots, hasSize(2));
    }

    @Test
    public void whenSplittingSlots_shouldGenerateValidSplits() {
        List<TimeSlot> splitSlots = timeSlot.split(halfSplit);
        assertEquals(now, splitSlots.get(0).getStart());
        assertEquals(future, splitSlots.get(1).getEnd());
    }

    @Test
    public void givenAnInstanceOutside_whenSplittingSlots_shouldThrowException() {
        Instant outlyingSplitPoint = now.minus(3, ChronoUnit.HOURS);
        assertThrows(IllegalArgumentException.class,
            () -> timeSlot.split(outlyingSplitPoint));
    }
}
