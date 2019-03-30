package org.kritiniyoga.karmayoga;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Validation;
import lombok.Value;
import org.kritiniyoga.karmayoga.validators.ScheduleValidator;

@Value
public class Schedule {
    private Task task;
    private TimeSlot slot;

    private Schedule(Task task, TimeSlot slot) {
        this.task = task;
        this.slot = slot;
    }

    public static Schedule createSchedule(TimeSlot slot, Task task) {
        Tuple2<Task, TimeSlot> taskSlotTuple = Tuple.of(task, slot);
        return Validation
            .combine(
                ScheduleValidator.checkSlotIsBeforeTaskEnds(taskSlotTuple),
                ScheduleValidator.checkTaskFitsSlot(taskSlotTuple))
            .ap((x, y) -> new Schedule(x._1, x._2))
            .get();
    }
}
