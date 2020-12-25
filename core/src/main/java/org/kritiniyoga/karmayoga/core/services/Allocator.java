package org.kritiniyoga.karmayoga.core.services;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.entities.Schedule;
import org.kritiniyoga.karmayoga.core.entities.Task;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;

public interface Allocator {
    Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots);
}
