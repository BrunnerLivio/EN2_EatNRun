package eatnrun.entities;

import eatnrun.Entity;
import gui.Window;

public class Cake extends Entity {

  public Cake(int x, int y) {
    super(x, y, 40, 40);
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/cake.png", x, y);
  }

}
