package org.kritiniyoga.karmayoga.core.application;

import java.util.Map;

public class CommandBus {
  Map<Command, CommandHandler> commandHandlerMap;

  void execute(Command command) {
    commandHandlerMap.get(command).handle(command);
  }

  void registerHandler(Command command, CommandHandler handler) {
    commandHandlerMap.put(command, handler);
  }
}
