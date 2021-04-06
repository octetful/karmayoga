package org.kritiniyoga.karmayoga.core.domain.entities;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;
import org.kritiniyoga.karmayoga.core.domain.values.Priority;

@Data
@Builder(toBuilder = true)
public class Task {
  UUID id;
  String title;
  String notes;
  Priority priority;
  Duration estimate;
  Date deadline;
}
