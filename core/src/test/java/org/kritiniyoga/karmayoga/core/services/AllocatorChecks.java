package org.kritiniyoga.karmayoga.core.services;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.core.entities.Schedule;
import org.kritiniyoga.karmayoga.core.entities.Task;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;


public abstract class AllocatorChecks {
    protected Allocator allocator;
    protected Task twoHoursTask;
    protected Task fiveHoursTask;
    protected TimeSlot twoHoursSlot;
    protected Seq<Schedule> schedules;

    @BeforeEach
    public void init() {
        twoHoursTask = Task.builder().estimate(Duration.ofHours(2)).build();
        fiveHoursTask = Task.builder().estimate(Duration.ofHours(5)).build();

        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        twoHoursSlot = TimeSlot.createTimeSlot(start, end);

        allocator = getAllocator();
    }

    protected abstract Allocator getAllocator();

    @Test
    public void givenTasksFittingSlots_whenAllocating_shouldAllocate() {
        schedules = allocator.allocate(List.of(twoHoursTask), List.of(twoHoursSlot));
        assertThat(schedules.get().getTask()).isEqualTo(twoHoursTask);
    }

    @Test
    public void givenTasksNotFittingSlots_whenAllocating_shouldNotAllocate() {
        schedules = allocator.allocate(List.of(fiveHoursTask), List.of(twoHoursSlot));
        assertThat(schedules).isEmpty();
    }

    @Test
    public void givenAMixOfFittingAndNonFittingTasks_whenAllocating_shouldAllocateFittingTasks() {
        schedules = allocator.allocate(List.of(fiveHoursTask, twoHoursTask), List.of(twoHoursSlot));
        assertThat(schedules.get().getTask()).isEqualTo(twoHoursTask);
    }
}
