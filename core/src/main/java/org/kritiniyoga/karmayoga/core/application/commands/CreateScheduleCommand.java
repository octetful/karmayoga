package org.kritiniyoga.karmayoga.core.application.commands;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateScheduleCommand {
  String taskId;
  String startTime;
  String endTime;
}
