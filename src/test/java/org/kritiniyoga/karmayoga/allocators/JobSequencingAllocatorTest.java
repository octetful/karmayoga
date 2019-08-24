package org.kritiniyoga.karmayoga.allocators;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.Priority;
import org.kritiniyoga.karmayoga.Schedule;
import org.kritiniyoga.karmayoga.Task;
import org.kritiniyoga.karmayoga.TimeSlot;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

class JobSequencingAllocatorTest {

    Allocator allocator;

    @BeforeEach
    public void setUp() {
        allocator = new JobSequencingAllocator();
    }

    @Test
    public void shouldAllocateTaskForGivenSlot() {
        Instant time = Instant.now();
        Task firstTask = Task.builder()
            .deadline(Date.from(time.plus(4, ChronoUnit.HOURS)))
            .estimate(Duration.ofHours(2)).build();

        Instant start = time.plus(1, ChronoUnit.HOURS);
        Instant end = time.plus(4, ChronoUnit.HOURS);
        TimeSlot firstSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedule = allocator.allocate(List.of(firstTask), List.of(firstSlot));

        assertThat(schedule).hasSize(1);
    }

    @Test
    public void shouldNotAllocateTaskIfEstimateNotFittingSlot() {
        Instant time = Instant.now();
        Task firstTask = Task.builder()
            .deadline(Date.from(time.plus(4, ChronoUnit.HOURS)))
            .estimate(Duration.ofHours(4)).build();

        Instant start = time.plus(1, ChronoUnit.HOURS);
        Instant end = time.plus(4, ChronoUnit.HOURS);
        TimeSlot firstSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedule = allocator.allocate(List.of(firstTask), List.of(firstSlot));

        assertThat(schedule).isEmpty();
    }

    @Test
    public void shouldNotAllocateTaskIfDeadlineNotFittingSlot() {
        Instant time = Instant.now();
        Task firstTask = Task.builder()
            .deadline(Date.from(time.plus(4, ChronoUnit.HOURS)))
            .estimate(Duration.ofHours(4))
            .build();

        Instant start = time.plus(1, ChronoUnit.HOURS);
        Instant end = time.plus(3, ChronoUnit.HOURS);
        TimeSlot firstSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedule = allocator.allocate(List.of(firstTask), List.of(firstSlot));

        assertThat(schedule).isEmpty();
    }

    @Test
    public void shouldAllocateTaskToFittingSlot() {
        Instant time = Instant.now();
        Task firstTask = Task.builder()
            .deadline(Date.from(time.plus(8, ChronoUnit.HOURS)))
            .estimate(Duration.ofHours(1))
            .priority(Priority.LOW)
            .build();

        Task secondTask = Task.builder()
            .deadline(Date.from(time.plus(6, ChronoUnit.HOURS)))
            .estimate(Duration.ofHours(2))
            .priority(Priority.HIGH)
            .build();

        Task thirdTask = Task.builder()
            .deadline(Date.from(time.plus(7, ChronoUnit.HOURS)))
            .estimate(Duration.ofHours(5))
            .priority(Priority.HIGH)
            .build();

        Instant firstSlotStart = time.plus(1, ChronoUnit.HOURS);
        Instant firstSlotEnd = time.plus(4, ChronoUnit.HOURS);
        TimeSlot firstSlot = TimeSlot.createTimeSlot(firstSlotStart, firstSlotEnd);

        Instant secondSlotStart = time.plus(4, ChronoUnit.HOURS);
        Instant secondSlotEnd = time.plus(7, ChronoUnit.HOURS);
        TimeSlot secondSlot = TimeSlot.createTimeSlot(secondSlotStart, secondSlotEnd);

        Seq<Schedule> schedule = allocator.allocate(List.of(firstTask, secondTask, thirdTask),
            List.of(firstSlot, secondSlot));

        Schedule firstSchedule = Schedule.createSchedule(firstSlot, secondTask);
        Schedule secondSchedule = Schedule.createSchedule(secondSlot, firstTask);
        Seq<Schedule> expectedSchedule = List.of(firstSchedule, secondSchedule);

        assertThat(schedule).isEqualTo(expectedSchedule);
    }

}
