package eatnrun.core.handler;

import eatnrun.core.Level;
import gui.Window;

public interface EventHandler {
  void handleEvents(Window window, Level level);
}
