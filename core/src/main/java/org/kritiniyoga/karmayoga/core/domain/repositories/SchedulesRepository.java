package org.kritiniyoga.karmayoga.core.domain.repositories;


import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;
import org.kritiniyoga.karmayoga.core.domain.values.TimeSlot;

public interface SchedulesRepository {
  Seq<Schedule> fetchAllSchedules();

  Schedule fetchById(Long id);
}
