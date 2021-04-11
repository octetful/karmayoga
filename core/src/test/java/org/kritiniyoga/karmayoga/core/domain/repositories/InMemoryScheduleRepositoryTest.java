package org.kritiniyoga.karmayoga.core.domain.repositories;


import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryScheduleRepositoryTest {
  @Test
  void shouldFindAddedSchedules() {
    var repository = new InMemoryScheduleRepository();
    var schedule = mock(Schedule.class);
    var scheduleId = UUID.randomUUID();
    when(schedule.getId()).thenReturn(scheduleId);

    repository.add(schedule);

    assertThat(repository.fetchById(scheduleId)).isPresent();
    assertThat(repository.fetchById(scheduleId)).contains(schedule);
  }
}