package org.kritiniyoga.karmayoga;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.allocators.AnotherSampleAllocator;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


public class SampleAllocatorChecks {
    private Allocator allocator;

    @BeforeEach
    void init() {
        allocator = new AnotherSampleAllocator();
    }

    @Test
    public void givenTasksFittingSlots_whenAllocating_shouldAllocate() {

        Task aTask = Task.builder().estimate(Duration.ofHours(2)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask), List.of(aSlot));

        assertThat(schedules.get().getTask()).isEqualTo(aTask);
    }

    @Test
    public void givenTasksNotFittingSlots_whenAllocating_shouldNotAllocate() {
        Task aTask = Task.builder().estimate(Duration.ofHours(5)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask), List.of(aSlot));

        assertThat(schedules).isEmpty();
    }

    @Test
    public void givenAMixOfFittingAndNonFittingTasks_whenAllocating_shouldAllocateFittingTasks() {
        Task aTask = Task.builder().estimate(Duration.ofHours(5)).build();
        Task bTask = Task.builder().estimate(Duration.ofHours(2)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask, bTask), List.of(aSlot));

        assertThat(schedules.get().getTask()).isEqualTo(bTask);
    }
}
