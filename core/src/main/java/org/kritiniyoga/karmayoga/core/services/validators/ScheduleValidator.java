package org.kritiniyoga.karmayoga.core.services.validators;

import io.vavr.Tuple2;
import io.vavr.control.Validation;
import org.kritiniyoga.karmayoga.core.entities.Task;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;


import java.sql.Date;
import java.util.function.Predicate;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

public class ScheduleValidator {

    private static final String ERROR_STRING_SLOT_AFTER_DEADLINE = "Slot must be before deadline";
    private static final String ERROR_STRING_TASK_BIGGER_THAN_SLOT = "Task too big for slot";

    private static Predicate<Tuple2<Task, TimeSlot>> isSlotBeforeDeadline =
        tuple2 -> tuple2._1.getDeadline() == null
            || tuple2._2.getStart().compareTo(tuple2._1.getDeadline().toInstant()) <= 0;

    private static Predicate<Tuple2<Task, TimeSlot>> isTaskFittingSlot =
        tuple2 -> tuple2._1.getEstimate() == null
            || tuple2._1.getEstimate().compareTo(tuple2._2.length()) <= 0;


    protected static Validation<String, Tuple2<Task, TimeSlot>> applyValidationPredicate(Tuple2<Task, TimeSlot> taskSlot, Predicate<Tuple2<Task, TimeSlot>> predicate, String error)  {
        return Match(taskSlot)
            .of(
                Case($(predicate),
                    Validation.valid(taskSlot)),
                Case($(),
                    Validation.invalid(error))
            );
    }

    public static Validation<String, Tuple2<Task, TimeSlot>> checkSlotIsBeforeTaskEnds(Tuple2<Task, TimeSlot> taskSlot)  {
        return applyValidationPredicate(taskSlot, isSlotBeforeDeadline,
            ERROR_STRING_SLOT_AFTER_DEADLINE
                + " [Slot Start: " + Date.from(taskSlot._2.getStart())
                + "Task End: " + taskSlot._1.getDeadline()
                + "]");
    }

    public static Validation<String, Tuple2<Task, TimeSlot>> checkTaskFitsSlot(Tuple2<Task, TimeSlot> taskSlot) {
        return applyValidationPredicate(taskSlot, isTaskFittingSlot,
            ERROR_STRING_TASK_BIGGER_THAN_SLOT
                + " [Slot Length: " + taskSlot._2.length()
                + "Task Estimate: " + taskSlot._1.getEstimate()
                + "]");
    }

}
