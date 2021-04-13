package org.kritiniyoga.karmayoga.core.domain.repositories;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

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
  public Task fetchById(UUID id) {
    return tasks.stream()
        .filter(task -> task.getId().equals(id))
        .findFirst()
        .get();
  }

  @Override
  public void add(Task task) {
    tasks.add(task);
  }

  @Override
  public void delete(UUID id) {
    tasks.remove(fetchById(id));
  }
}
