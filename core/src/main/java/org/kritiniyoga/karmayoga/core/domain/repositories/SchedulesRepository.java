package org.kritiniyoga.karmayoga.core.domain.repositories;


import io.vavr.collection.Seq;
import java.util.Optional;
import java.util.UUID;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;

public interface SchedulesRepository {
  Seq<Schedule> fetchAllSchedules();

  Optional<Schedule> fetchById(UUID id);

  void add(Schedule schedule);
}
