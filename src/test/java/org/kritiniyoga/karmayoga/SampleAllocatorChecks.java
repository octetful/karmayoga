package org.kritiniyoga.karmayoga;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.allocators.AnotherSampleAllocator;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


public class SampleAllocatorChecks {
    @Test
    public void givenSlotsFittingTasks_whenAllocating_shouldAllocate() {
        Allocator allocator = new AnotherSampleAllocator();

        Task aTask = Task.builder().estimate(Duration.ofHours(2)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask), List.of(aSlot));

        assertThat(schedules).isNotNull();
        assertThat(schedules.get()).isNotNull();
    }
}
