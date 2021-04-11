package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;

public class InMemoryTaskRepository implements TasksRepository {
  private final Set<Task> tasks;

  public InMemoryTaskRepository() {
    tasks = new HashSet<>();
  }

  @Override
  public Seq<Task> fetchAllTasks() {
    return List.ofAll(tasks);
  }

  @Override
  public Optional<Task> fetchById(UUID id) {
    return tasks.stream()
        .filter(task -> task.getId().equals(id))
        .findFirst();
  }

  @Override
  public void add(Task task) {
    tasks.add(task);
  }
}
