package org.kritiniyoga.karmayoga.core.domain.services.allocators;

import io.vavr.collection.Seq;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;
import org.kritiniyoga.karmayoga.core.domain.entities.Schedule;
import org.kritiniyoga.karmayoga.core.domain.entities.Task;
import org.kritiniyoga.karmayoga.core.domain.values.Priority;
import org.kritiniyoga.karmayoga.core.domain.values.TimeSlot;

public class FirstFitWithGreedyTasks extends SimpleFirstFit {
  private static final Date MAX_DATE = new Date(Long.MAX_VALUE);

  Comparator<Task> deadlineComparator = (a, b) -> {
    Date firstDeadline = (a.getDeadline() == null) ? MAX_DATE : a.getDeadline();
    Date secondDeadline = (b.getDeadline() == null) ? MAX_DATE : b.getDeadline();

    return firstDeadline.compareTo(secondDeadline);
  };

  Comparator<Task> priorityComparator = (a, b) -> {
    Priority firstPriority = (a.getPriority() == null) ? Priority.LOW : a.getPriority();
    Priority secondPriority = (b.getPriority() == null) ? Priority.LOW : b.getPriority();

    return firstPriority.compareTo(secondPriority);
  };

  Comparator<Task> estimateComparator = (a, b) -> {
    Duration firstEstimate = (a.getEstimate() == null) ? Duration.ZERO : a.getEstimate();
    Duration secondEstimate = (b.getEstimate() == null) ? Duration.ZERO : b.getEstimate();

    return firstEstimate.compareTo(secondEstimate);
  };

  @Override
  public Seq<Schedule> allocate(Seq<Task> tasks, Seq<TimeSlot> slots) {
    Seq<Task> sortedTasks = tasks.sorted(deadlineComparator)
        .sorted(estimateComparator)
        .sorted(priorityComparator.reversed());

    return super.allocate(sortedTasks, slots);
  }
}
