package org.kritiniyoga.karmayoga.core.entities;

import lombok.Builder;
import lombok.Data;
import org.kritiniyoga.karmayoga.core.values.Priority;

import java.time.Duration;
import java.util.Date;

@Data
@Builder(toBuilder = true)
public class Task {
    String title, notes;
    Priority priority;
    Duration estimate;
    Date deadline;
}
