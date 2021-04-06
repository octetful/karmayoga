package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.Seq;
import java.time.Duration;
import java.util.Date;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;
import org.kritiniyoga.karmayoga.core.domain.values.Priority;

public interface TasksRepository {
  Seq<Task> fetchAllTasks();

  Seq<Task> fetchTasksWithPriority(Priority priority);

  Seq<Task> fetchTasksWithEstimate(Duration estimate);

  Seq<Task> fetchTasksWithGivenDeadline(Date deadline);

  Seq<Task> fetchTasksMatchingTitle(String title);

  Seq<Task> fetchTasksMatchingNotes(String notes);

  Task fetchById(Long id);
}
