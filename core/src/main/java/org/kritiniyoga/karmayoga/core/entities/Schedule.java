package org.kritiniyoga.karmayoga.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.kritiniyoga.karmayoga.core.values.TimeSlot;


@Data
@AllArgsConstructor
public class Schedule {
    Task task;
    TimeSlot slot;
}
