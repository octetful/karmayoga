package org.kritiniyoga.karmayoga.core.domain.services;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;
import org.kritiniyoga.karmayoga.core.domain.values.TimeSlot;

public interface Allocator {
  Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots);
}
