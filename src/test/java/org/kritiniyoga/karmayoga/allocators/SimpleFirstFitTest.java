package org.kritiniyoga.karmayoga.allocators;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.Schedule;
import org.kritiniyoga.karmayoga.Task;
import org.kritiniyoga.karmayoga.TimeSlot;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

class SimpleFirstFitTest {
    private Task twoHoursTask;
    private Task fiveHoursTask;
    private TimeSlot sevenHoursSlot;
    private Allocator allocator;

    @BeforeEach
    public void init() {
        allocator = new SimpleFirstFit();

        twoHoursTask = Task.builder().estimate(Duration.ofHours(2)).build();
        fiveHoursTask = Task.builder().estimate(Duration.ofHours(5)).build();

        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(9, ChronoUnit.HOURS);
        sevenHoursSlot = TimeSlot.createTimeSlot(start, end);
    }

    @Test
    public void givenTwoTasksAndASlotLargeEnough_whenAllocating_shouldSplitAndAllocate() {
        Seq<Schedule> schedules = allocator.allocate(List.of(twoHoursTask, fiveHoursTask), List.of(sevenHoursSlot));
        Assertions.assertThat(schedules.get(0).getTask()).isEqualTo(twoHoursTask);
        Assertions.assertThat(schedules.get(0).getSlot().length().toHours()).isEqualTo(2);
        Assertions.assertThat(schedules.get(1).getTask()).isEqualTo(fiveHoursTask);
        Assertions.assertThat(schedules.get(1).getSlot().length().toHours()).isEqualTo(5);
    }
}
