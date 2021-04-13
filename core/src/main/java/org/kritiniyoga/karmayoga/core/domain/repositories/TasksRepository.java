package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;

import java.util.UUID;

public interface TasksRepository {
  Seq<Task> fetchAllTasks();

  Task fetchById(UUID id);

  void add(Task task);

  void delete(UUID id);
}
