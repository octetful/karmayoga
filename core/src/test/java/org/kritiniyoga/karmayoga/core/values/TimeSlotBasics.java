package org.kritiniyoga.karmayoga.core.values;

import io.vavr.collection.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
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
        timeSlot = TimeSlot.createFrom(now, future);
        halfSplit = now.plus(1, ChronoUnit.HOURS);
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

    @Test
    public void givenAnInstanceWithin_shouldBeAbleToSpitASlot() {
        List<TimeSlot> splitSlots = timeSlot.split(halfSplit);
        Assertions.assertThat(splitSlots).hasSize(2);
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
        assertThrows(NoSuchElementException.class,
            () -> timeSlot.split(outlyingSplitPoint));
    }
}
