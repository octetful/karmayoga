package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.Seq;
import java.time.Duration;
import java.util.Date;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;
import org.kritiniyoga.karmayoga.core.domain.values.Priority;

public interface TasksRepository {
  Seq<Task> fetchAllTasks();

  Task fetchById(Long id);
}
