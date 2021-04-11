package org.kritiniyoga.karmayoga.core.domain.entities;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kritiniyoga.karmayoga.core.domain.values.TimeSlot;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class Schedule {
  UUID id;
  Task task;
  TimeSlot slot;
  User owner;
}
