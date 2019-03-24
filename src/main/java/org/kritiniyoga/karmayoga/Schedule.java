package org.kritiniyoga.karmayoga;

import lombok.Value;

import java.time.Duration;

@Value
public class Schedule {
    TimeSlot slot;
    Task task;

    public Schedule(TimeSlot slot, Task task)
            throws IllegalArgumentException {
        checkParameters(slot, task);
        this.slot = slot;
        this.task = task;
    }

    private void checkParameters(TimeSlot slot, Task task)
            throws IllegalArgumentException {
        if (taskHasDeadline(task)
                && isSlotAfterDeadline(slot, task)) {
            throw new IllegalArgumentException("Slot cannot start after task deadline!");
        } else if (taskHasEstimate(task)
                && isTaskEstimateLargerThanSlot(slot, task)) {
            throw new IllegalArgumentException("Slot is too small for task");
        }
    }

    private boolean isTaskEstimateLargerThanSlot(TimeSlot slot, Task task) {
        return task.getEstimate().compareTo(
            Duration.between(slot.getStart(), slot.getEnd())) > 0;
    }

    private boolean taskHasEstimate(Task task) {
        return task.getEstimate() != null;
    }

    private boolean isSlotAfterDeadline(TimeSlot slot, Task task) {
        return slot.getStart().isAfter(task.getDeadline().toInstant());
    }

    private boolean taskHasDeadline(Task task) {
        return task.getDeadline() != null;
    }
}
