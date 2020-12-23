package org.kritiniyoga.karmayoga.core.services.allocators;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.List;
import io.vavr.collection.Seq;
import io.vavr.collection.TreeSet;
import org.kritiniyoga.karmayoga.core.entities.Schedule;
import org.kritiniyoga.karmayoga.core.entities.Task;
import org.kritiniyoga.karmayoga.core.factories.ScheduleFactory;
import org.kritiniyoga.karmayoga.core.services.Allocator;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;


import java.time.Instant;

public class SimpleFirstFit implements Allocator {

    private Tuple2<List<Schedule>, TreeSet<TimeSlot>> directlyAllocate(Tuple2<List<Schedule>, TreeSet<TimeSlot>> scheduleSlotTuple, TimeSlot currentSlot, Task task) {
        return Tuple.of(
            scheduleSlotTuple._1.append(ScheduleFactory.createFromOrFail(currentSlot, task)),
            scheduleSlotTuple._2
        );
    }

    private Instant findSplitPoint(TimeSlot slot, Task task) {
        return slot.getStart().plus(task.getEstimate());
    }

    private Tuple2<List<Schedule>, TreeSet<TimeSlot>> splitSlotAndAllocate(Tuple2<List<Schedule>, TreeSet<TimeSlot>> scheduleSlotTuple, TimeSlot currentSlot, Task task) {
        List<TimeSlot> splits = currentSlot.split(findSplitPoint(currentSlot, task));
        return Tuple.of(
            scheduleSlotTuple._1.append(ScheduleFactory.createFromOrFail(splits.get(0), task)),
            scheduleSlotTuple._2.add(splits.get(1))
        );
    }

    private boolean isSlotLargerThanTask(TimeSlot slot, Task task) {
        return slot.length().compareTo(task.getEstimate()) > 0;
    }

    private Tuple2<List<Schedule>, TreeSet<TimeSlot>> allocateTaskToSlot(Tuple2<List<Schedule>, TreeSet<TimeSlot>> scheduleSlotTuple, TimeSlot currentSlot, Task task) {
        if ( (isSlotLargerThanTask(currentSlot, task)) ) {
            return splitSlotAndAllocate(scheduleSlotTuple, currentSlot, task);
        } else {
            return directlyAllocate(scheduleSlotTuple, currentSlot, task);
        }
    }

    private Tuple2<List<Schedule>, TreeSet<TimeSlot>> applyFirstFitAlgorithm(Tuple2<List<Schedule>, TreeSet<TimeSlot>> scheduleSlotTuple, Task task) {
        return scheduleSlotTuple._2
            .find(timeSlot -> ScheduleFactory.canCreateScheduleFrom(timeSlot, task))
            .map(timeSlot -> allocateTaskToSlot(scheduleSlotTuple, timeSlot, task))
            .getOrElse(scheduleSlotTuple);
    }

    private Seq<Schedule> firstFitAllocation(Seq<Task> tasks, Seq<TimeSlot> slots, List<Schedule> schedules) {
        return tasks.foldLeft(Tuple.of(schedules, TreeSet.ofAll(slots)), this::applyFirstFitAlgorithm)._1;
    }

    @Override
    public Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots) {
        return firstFitAllocation(tasks, slots, List.empty());
    }

}
