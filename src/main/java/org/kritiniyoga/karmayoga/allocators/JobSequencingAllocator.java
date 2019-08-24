package org.kritiniyoga.karmayoga.allocators;

import io.vavr.collection.List;
import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.Allocator;
import org.kritiniyoga.karmayoga.Schedule;
import org.kritiniyoga.karmayoga.Task;
import org.kritiniyoga.karmayoga.TimeSlot;

import java.util.Comparator;

public class JobSequencingAllocator implements Allocator {

    @Override
    public Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots) {

        List<Schedule> schedules = List.of();
        for (Task task : sort(tasks)) {
            TimeSlot slot = getSlot(slots, task);
            slots = slots.remove(slot);
            if (slot != null) {
                Schedule schedule = Schedule.createSchedule(slot, task);
                schedules = schedules.append(schedule);
            }

        }
        return schedules;

    }

    private TimeSlot getSlot(Seq<TimeSlot> slots, Task task) {
        Seq<TimeSlot> slotsFittingTask = slots.filter(timeSlot -> task.getEstimate().compareTo(timeSlot.length()) < 0
            && task.getDeadline().toInstant().compareTo(timeSlot.getStart()) > 0)
            .sorted(Comparator.comparing(TimeSlot::getEnd));
        return !slotsFittingTask.isEmpty() ? slotsFittingTask.get(0) : null;
    }

    private Seq<Task> sort(Seq<Task> tasks) {
        return tasks.sorted(Comparator.comparing(Task::getPriority)).reverse()
            .groupBy(task -> task.getPriority())
            .flatMap(priorityTasks -> priorityTasks._2.sorted(Comparator.comparing(Task::getEstimate)));

    }
}
