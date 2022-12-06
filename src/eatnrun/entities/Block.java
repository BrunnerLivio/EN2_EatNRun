package eatnrun.entities;

import eatnrun.Entity;
import gui.Window;

public class Block extends Entity {
  public Block(int x, int y) {
    super(x, y, 40, 40);
  }

  @Override
  public void draw(Window window) {
    window.setColor(0, 0, 0);
    window.fillRect(x - width / 2, y - height / 2, width, height);
  }

}
