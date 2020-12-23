package org.kritiniyoga.karmayoga.core.entities;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import lombok.Value;
import org.kritiniyoga.karmayoga.core.services.validators.ScheduleValidator;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;


@Value
public class Schedule {
    Task task;
    TimeSlot slot;

    private Schedule(Task task, TimeSlot slot) {
        this.task = task;
        this.slot = slot;
    }

    private static Validation<Seq<String>, Schedule> validatedSchedule(TimeSlot slot, Task task) {
        Tuple2<Task, TimeSlot> taskSlotTuple = Tuple.of(task, slot);
        return Validation
            .combine(
                ScheduleValidator.checkSlotIsBeforeTaskEnds(taskSlotTuple),
                ScheduleValidator.checkTaskFitsSlot(taskSlotTuple))
            .ap((result1, result2) -> new Schedule(task, slot));
    }

    public static Schedule createScheduleOrFail(TimeSlot slot, Task task) {
        Validation<Seq<String>, Schedule> vResult = validatedSchedule(slot, task);
        return vResult
            .getOrElseThrow(() -> new IllegalArgumentException(vResult.getError().toString()));
    }

    public static boolean areValidScheduleParams(TimeSlot slot, Task task) {
        return validatedSchedule(slot, task).isValid();
    }
}
