package org.kritiniyoga.karmayoga;

import io.vavr.collection.Seq;

public interface Allocator {
    Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots);
}
