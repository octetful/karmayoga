package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;

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
  public Optional<Schedule> fetchById(UUID id) {
    return schedules.stream()
        .filter(schedule -> schedule.getId().equals(id))
        .findFirst();
  }

  @Override
  public void add(Schedule schedule) {
    schedules.add(schedule);
  }
}
