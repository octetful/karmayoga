package org.kritiniyoga.karmayoga.allocators;

import io.vavr.collection.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.Priority;
import org.kritiniyoga.karmayoga.Task;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

class FirstFitWithGreedyTasksTest extends SimpleFirstFitTest {
    @Override
    protected Allocator getAllocator() {
        return new FirstFitWithGreedyTasks();
    }

    private Task lowEstimateHighPriorityTask;
    private Task highEstimateHighPriorityTask;
    private Task lowEstimateLowPriorityTask;
    private Task lowEstimateHighPriorityTaskWithoutDeadline;
    private Task lowEstimateHighPriorityTaskWithDeadline;

    @BeforeEach
    public void init() {
        super.init();
        lowEstimateHighPriorityTask = Task.builder()
            .priority(Priority.HIGH)
            .estimate(Duration.ofHours(2))
            .build();
    }

    @Test
    public void givenTwoTasksWithDifferentEstimateAndSamePriority_shouldScheduleLowEstimateTaskFirst() {
        highEstimateHighPriorityTask = Task.builder()
            .priority(Priority.HIGH)
            .estimate(Duration.ofHours(5))
            .build();
        schedules = allocator.allocate(List.of(lowEstimateHighPriorityTask, highEstimateHighPriorityTask),
            List.of(sevenHoursSlot));
        Assertions.assertThat(schedules.get(0).getTask()).isEqualTo(lowEstimateHighPriorityTask);
        Assertions.assertThat(schedules.get(1).getTask()).isEqualTo(highEstimateHighPriorityTask);
    }

    @Test
    public void givenTwoTasksWithAndWithoutDeadline_shouldScheduleTaskWithDeadlineFirst() {
        lowEstimateHighPriorityTaskWithoutDeadline = Task.builder()
            .estimate(Duration.ofHours(2))
            .priority(Priority.HIGH)
            .build();
        lowEstimateHighPriorityTaskWithDeadline = Task.builder()
            .estimate(Duration.ofHours(2))
            .priority(Priority.HIGH)
            .deadline(Date.from(Instant.now().plus(3, ChronoUnit.HOURS)))
            .build();
        schedules = allocator.allocate(
            List.of(lowEstimateHighPriorityTaskWithoutDeadline, lowEstimateHighPriorityTaskWithDeadline),
            List.of(sevenHoursSlot));
        Assertions.assertThat(schedules.get(0).getTask()).isEqualTo(lowEstimateHighPriorityTaskWithDeadline);
        Assertions.assertThat(schedules.get(1).getTask()).isEqualTo(lowEstimateHighPriorityTaskWithoutDeadline);
    }

    @Test
    public void givenTwoTasksWithSameEstimateAndDifferentPriority_shouldScheduleHighPriorityTaskFirst() {
        lowEstimateLowPriorityTask = Task.builder()
            .estimate(Duration.ofHours(2))
            .priority(Priority.LOW)
            .build();
        schedules = allocator.allocate(List.of(lowEstimateHighPriorityTask, lowEstimateLowPriorityTask),
            List.of(sevenHoursSlot));
        Assertions.assertThat(schedules.get(0).getTask()).isEqualTo(lowEstimateHighPriorityTask);
        Assertions.assertThat(schedules.get(1).getTask()).isEqualTo(lowEstimateLowPriorityTask);
    }
}
