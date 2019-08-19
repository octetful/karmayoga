package org.kritiniyoga.karmayoga;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.kritiniyoga.karmayoga.allocators.AnotherSampleAllocator;
import org.kritiniyoga.karmayoga.allocators.SampleAllocator;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class AllocatorChecks {

    static Stream<Allocator> generateAllocators() {
        return Stream.of(new AnotherSampleAllocator());
    }

    @ParameterizedTest
    @MethodSource("generateAllocators")
    public void givenTasksFittingSlots_whenAllocating_shouldAllocate(Allocator allocator) {
        Task aTask = Task.builder().estimate(Duration.ofHours(2)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask), List.of(aSlot));

        assertThat(schedules.get().getTask()).isEqualTo(aTask);
    }

    @ParameterizedTest
    @MethodSource("generateAllocators")
    public void givenTasksNotFittingSlots_whenAllocating_shouldNotAllocate(Allocator allocator) {
        Task aTask = Task.builder().estimate(Duration.ofHours(5)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask), List.of(aSlot));

        assertThat(schedules).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("generateAllocators")
    public void givenAMixOfFittingAndNonFittingTasks_whenAllocating_shouldAllocateFittingTasks(Allocator allocator) {
        Task aTask = Task.builder().estimate(Duration.ofHours(5)).build();
        Task bTask = Task.builder().estimate(Duration.ofHours(2)).build();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        TimeSlot aSlot = TimeSlot.createTimeSlot(start, end);

        Seq<Schedule> schedules = allocator.allocate(List.of(aTask, bTask), List.of(aSlot));

        assertThat(schedules.get().getTask()).isEqualTo(bTask);
    }
}
