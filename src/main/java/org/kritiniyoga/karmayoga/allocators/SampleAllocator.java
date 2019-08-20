package org.kritiniyoga.karmayoga.allocators;

import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.Schedule;
import org.kritiniyoga.karmayoga.Task;
import org.kritiniyoga.karmayoga.TimeSlot;

import java.util.Comparator;
import java.util.Optional;

public class SampleAllocator implements Allocator {

    @Override
    public Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots) {
        return tasks.groupBy(task -> findFittingSlotForTask(slots, task))
            .filter(this::hasEmptySlot)
            .iterator()
            .map(this::createSchedules)
            .toList();

    }

    private Schedule createSchedules(Tuple2<Optional<TimeSlot>,? extends Seq<Task>> optionalTuple2) {
        Task task = selectFirstTaskByPriorityAndEstimate(optionalTuple2._2);
        TimeSlot slot = optionalTuple2._1.get();
        return Schedule.createSchedule(slot, task);
    }

    private boolean hasEmptySlot(Tuple2<Optional<TimeSlot>, ? extends Seq<Task>> slotTaskTuple) {
        return slotTaskTuple._1.isPresent();
    }

    private Optional<TimeSlot> findFittingSlotForTask(Seq<TimeSlot> slots, Task task) {
        return slots.find(slot ->
                slot.length().compareTo(task.getEstimate()) >= 0)
            .toJavaOptional();
    }

    private Task selectFirstTaskByPriorityAndEstimate(Seq<Task> tasks) {
        return tasks.sorted(Comparator.comparing(Task::getPriority)
            .reversed()
            .thenComparing(Task::getEstimate))
            .get();
    }
}
