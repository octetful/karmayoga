package org.kritiniyoga.karmayoga;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.kritiniyoga.karmayoga.allocators.AnotherSampleAllocator;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


public class AllocatorChecks {

    private Task twoHoursTask;
    private Task fiveHoursTask;
    private TimeSlot twoHoursSlot;

    @BeforeEach
    public void init() {
        twoHoursTask = Task.builder().estimate(Duration.ofHours(2)).build();
        fiveHoursTask = Task.builder().estimate(Duration.ofHours(5)).build();

        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(4, ChronoUnit.HOURS);
        twoHoursSlot = TimeSlot.createTimeSlot(start, end);
    }

    static Stream<Allocator> generateAllocators() {
        return Stream.of(new AnotherSampleAllocator());
    }

    @ParameterizedTest
    @MethodSource("generateAllocators")
    public void givenTasksFittingSlots_whenAllocating_shouldAllocate(Allocator allocator) {
        Seq<Schedule> schedules = allocator.allocate(List.of(twoHoursTask), List.of(twoHoursSlot));
        assertThat(schedules.get().getTask()).isEqualTo(twoHoursTask);
    }

    @ParameterizedTest
    @MethodSource("generateAllocators")
    public void givenTasksNotFittingSlots_whenAllocating_shouldNotAllocate(Allocator allocator) {
        Seq<Schedule> schedules = allocator.allocate(List.of(fiveHoursTask), List.of(twoHoursSlot));
        assertThat(schedules).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("generateAllocators")
    public void givenAMixOfFittingAndNonFittingTasks_whenAllocating_shouldAllocateFittingTasks(Allocator allocator) {
        Seq<Schedule> schedules = allocator.allocate(List.of(fiveHoursTask, twoHoursTask), List.of(twoHoursSlot));
        assertThat(schedules.get().getTask()).isEqualTo(twoHoursTask);
    }
}
