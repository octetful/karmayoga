package org.kritiniyoga.karmayoga.allocators;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.Schedule;
import org.kritiniyoga.karmayoga.Task;
import org.kritiniyoga.karmayoga.TimeSlot;

public class SampleAllocator implements Allocator {
    @Override
    public Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots) {
        return tasks.zip(slots)
            .map(zippedTasksSlots ->
                Schedule.createSchedule(zippedTasksSlots._2, zippedTasksSlots._1));
    }
}
