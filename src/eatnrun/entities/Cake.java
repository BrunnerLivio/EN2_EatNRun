package eatnrun.entities;

import eatnrun.core.Entity;
import eatnrun.core.Level;
import gui.Window;

public class Cake extends Entity {

  public Cake(Level level, int x, int y) {
    super(level, x, y, 40, 40);
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/cake.png", x, y);
  }
}
