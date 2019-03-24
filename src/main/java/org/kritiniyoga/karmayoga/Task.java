package org.kritiniyoga.karmayoga;

import lombok.Builder;
import lombok.Value;

import java.time.Duration;
import java.util.Date;

@Value
@Builder
public final class Task {
    String title, notes;
    Priority priority;
    Duration estimate;
    Date deadline;
    TimeSlot slot;
}
