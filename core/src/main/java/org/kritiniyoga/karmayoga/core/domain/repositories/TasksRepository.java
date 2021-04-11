package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.Seq;
import java.util.Optional;
import java.util.UUID;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;

public interface TasksRepository {
  Seq<Task> fetchAllTasks();

  Optional<Task> fetchById(UUID id);

  void add(Task task);
}
