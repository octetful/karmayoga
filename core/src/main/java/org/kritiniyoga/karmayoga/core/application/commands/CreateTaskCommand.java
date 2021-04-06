package org.kritiniyoga.karmayoga.core.application.commands;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateTaskCommand {
  String taskTitle;
  String taskNotes;
  String taskPriority;
  String taskDeadline;
  String taskDuration;
}
