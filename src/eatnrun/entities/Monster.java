package eatnrun.entities;

import eatnrun.core.Entity;
import eatnrun.core.Level;
import eatnrun.core.MoveableEntity;
import eatnrun.core.handler.CollisionHandler;
import eatnrun.core.handler.StepHandler;
import gui.Window;

public class Monster extends MoveableEntity implements CollisionHandler, StepHandler {
  private int vx;
  private int vy;

  public Monster(Level level, int x, int y, int vx, int vy) {
    super(level, x, y, 40, 40, 5);
    this.vx = vx;
    this.vy = vy;
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/monster.png", x, y);
  }

  private void bounce() {
    vx = vx * -1;
    vy = vy * -1;
  }

  @Override
  public void onCollide(Entity contact) {
    if (contact instanceof Block) {
      bounce();
    }
  }

  @Override
  public void onStep() {
    x = x + vx;
    y = y + vy;
  }
}
