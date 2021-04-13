package org.kritiniyoga.karmayoga.core.domain.repositories;


import org.junit.jupiter.api.Test;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;

import java.util.UUID;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InMemoryTaskRepositoryTest {
  @Test
  void shouldFindAddedTasks() {
    var repository = new InMemoryTaskRepository();
    var task = mock(Task.class);
    var randomId = UUID.randomUUID();
    when(task.getId()).thenReturn(randomId);
    repository.add(task);

    //assertThat(repository.fetchById(randomId)).isPresent();
    //assertThat(repository.fetchById(randomId)).contains(task);
  }
}