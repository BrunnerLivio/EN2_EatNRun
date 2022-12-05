package eatnrun.entities;

import gui.Window;

public class Player extends GameObject {

  public Player(int x, int y) {
    super(x, y, 50, 50);
  }

  public void moveUp() {
    move(0, -5);
  }

  public void moveDown() {
    move(0, 5);
  }

  public void moveRight() {
    move(5, 0);
  }

  public void moveLeft() {
    move(-5, 0);
  }

  public void draw(Window window) {
    window.drawImageCentered("resources/images/hero.png", x, y);
  }
}
