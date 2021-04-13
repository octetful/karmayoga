package org.kritiniyoga.karmayoga.rest.controllers;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;
import org.kritiniyoga.karmayoga.core.domain.repositories.SchedulesRepository;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("karmayoga/schedules")
public class ScheduleController {

    private SchedulesRepository schedulesRepository;

    @GetMapping("{id}")
    public Schedule getScheduleById(@PathVariable UUID id){
        return  schedulesRepository.fetchById(id);
    }

    @GetMapping
    public Seq<Schedule> getSchedules(){
        return schedulesRepository.fetchAllSchedules();
    }

    @PostMapping
    public void saveSchedule(@RequestBody Schedule schedule){
        schedulesRepository.add(schedule);
    }

    @DeleteMapping("{id}")
    public void deleteSchedule(@PathVariable UUID id){
        schedulesRepository.delete(id);
    }
}
