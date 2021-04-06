package org.kritiniyoga.karmayoga.core.application.commands;

import lombok.Builder;
import lombok.Value;

@Value
public class DeleteTaskCommand {
  String taskId;
}
