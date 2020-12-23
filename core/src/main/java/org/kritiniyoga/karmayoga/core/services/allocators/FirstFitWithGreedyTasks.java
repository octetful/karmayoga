package org.kritiniyoga.karmayoga.core.services.allocators;

import io.vavr.collection.Seq;
import org.kritiniyoga.karmayoga.core.entities.Schedule;
import org.kritiniyoga.karmayoga.core.entities.Task;
import org.kritiniyoga.karmayoga.core.values.Priority;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;


import java.time.Duration;
import java.util.Comparator;
import java.util.Date;

public class FirstFitWithGreedyTasks extends SimpleFirstFit {
    private static Date MAX_DATE = new Date(Long.MAX_VALUE);

    Comparator<Task> deadlineComparator = (a, b) -> {
        Date aDeadline = (a.getDeadline() == null) ? MAX_DATE : a.getDeadline();
        Date bDeadline = (b.getDeadline() == null) ? MAX_DATE : b.getDeadline();

        return aDeadline.compareTo(bDeadline);
    };

    Comparator<Task> priorityComparator = (a, b) -> {
        Priority aPriority = (a.getPriority() == null) ? Priority.LOW : a.getPriority();
        Priority bPriority = (b.getPriority() == null) ? Priority.LOW : b.getPriority();

        return aPriority.compareTo(bPriority);
    };

    Comparator<Task> estimateComparator = (a, b) -> {
        Duration aEstimate = (a.getEstimate() == null) ? Duration.ZERO : a.getEstimate();
        Duration bEstimate = (b.getEstimate() == null) ? Duration.ZERO : b.getEstimate();

        return aEstimate.compareTo(bEstimate);
    };

    @Override
    public Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots) {
        Seq<Task> sortedTasks = tasks.sorted(deadlineComparator)
                .sorted(estimateComparator)
                .sorted(priorityComparator.reversed());

        return super.allocate(sortedTasks, slots);
    }
}
