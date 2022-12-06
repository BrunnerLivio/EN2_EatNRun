package eatnrun.entities;

import eatnrun.core.MoveableEntity;
import gui.Window;

public class Monster extends MoveableEntity {
  private int vx;
  private int vy;

  public Monster(int x, int y, int vx, int vy) {
    super(x, y, 40, 40, 5);
    this.vx = vx;
    this.vy = vy;
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/monster.png", x, y);
  }

  public void step() {
    x = x + vx;
    y = y + vy;
  }

  public void bounce() {
    vx = vx * -1;
    vy = vy * -1;

  }
}
