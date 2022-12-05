package eatnrun.entities;

import gui.Window;

public class Player extends MoveableEntity {

  public Player(int x, int y) {
    super(x, y, 40, 40, 5);
  }

  public void draw(Window window) {
    window.drawImageCentered("resources/images/hero.png", x, y);
  }
}
