package org.kritiniyoga.karmayoga.core.services.allocators;

import io.vavr.collection.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.core.services.Allocator;
import org.kritiniyoga.karmayoga.core.services.AllocatorChecks;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

class SimpleFirstFitTest extends AllocatorChecks {
    protected TimeSlot sevenHoursSlot;

    @BeforeEach
    public void init() {
        super.init();
        Instant start = Instant.now().plus(2, ChronoUnit.HOURS);
        Instant end = Instant.now().plus(9, ChronoUnit.HOURS);
        sevenHoursSlot = TimeSlot.createFrom(start, end);
    }

    @Override
    protected Allocator getAllocator() {
        return new SimpleFirstFit();
    }

    @Test
    public void givenTwoTasksAndASlotLargeEnough_whenAllocating_shouldSplitAndAllocate() {
        schedules = allocator.allocate(List.of(twoHoursTask, fiveHoursTask), List.of(sevenHoursSlot));
        Assertions.assertThat(schedules.get(0).getTask()).isEqualTo(twoHoursTask);
        Assertions.assertThat(schedules.get(0).getSlot().length().toHours()).isEqualTo(2);
        Assertions.assertThat(schedules.get(1).getTask()).isEqualTo(fiveHoursTask);
        Assertions.assertThat(schedules.get(1).getSlot().length().toHours()).isEqualTo(5);
    }
}
