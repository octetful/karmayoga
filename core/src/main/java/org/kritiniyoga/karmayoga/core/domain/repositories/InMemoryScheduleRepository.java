package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class InMemoryScheduleRepository implements SchedulesRepository {
  private final Set<Schedule> schedules;

  public InMemoryScheduleRepository() {
    schedules = new HashSet<>();
  }

  @Override
  public Seq<Schedule> fetchAllSchedules() {
    return List.ofAll(schedules);
  }

  @Override
  public Schedule fetchById(UUID id) {
    return schedules.stream()
        .filter(s -> s.getId().equals(id))
        .findFirst()
        .get();
  }

  @Override
  public void add(Schedule schedule) {
    schedules.add(schedule);
  }

  @Override
  public void delete(UUID id) {
    schedules.remove(fetchById(id));
  }
}
