package org.kritiniyoga.karmayoga.core.domain.repositories;


import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;

import java.util.UUID;

public interface SchedulesRepository {
  Seq<Schedule> fetchAllSchedules();

  Schedule fetchById(UUID id);

  void add(Schedule schedule);

  void delete(UUID id);
}
