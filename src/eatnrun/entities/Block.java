package eatnrun.entities;

import eatnrun.core.Entity;
import eatnrun.core.Level;
import gui.Window;

public class Block extends Entity {
  public Block(Level level, int x, int y) {
    super(level, x, y, 40, 40);
  }

  @Override
  public void draw(Window window) {
    window.setColor(0, 0, 0);
    window.fillRect(x - width / 2, y - height / 2, width, height);
  }
}
