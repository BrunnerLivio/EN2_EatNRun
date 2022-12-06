package eatnrun.entities;

import eatnrun.Entity;
import gui.Window;

public class Finish extends Entity {

  public Finish(int x, int y) {
    super(x, y, 40, 40);
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/finish.png", x, y);
  }



}
