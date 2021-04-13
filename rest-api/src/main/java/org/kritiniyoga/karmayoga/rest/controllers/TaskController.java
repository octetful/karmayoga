package org.kritiniyoga.karmayoga.rest.controllers;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;
import org.kritiniyoga.karmayoga.core.domain.repositories.TasksRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("karmayoga/tasks")
public class TaskController {

    private TasksRepository tasksRepository;

    @GetMapping("{id}")
    public Task getTaskById(@PathVariable UUID id){
        return  tasksRepository.fetchById(id);
    }

    @GetMapping
    public Seq<Task> getTasks(){
        return tasksRepository.fetchAllTasks();
    }

    @PostMapping
    public void saveTask(@RequestBody Task task){
        tasksRepository.add(task);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable UUID id){
        tasksRepository.delete(id);
    }
}
