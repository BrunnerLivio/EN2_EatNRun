package eatnrun.entities;

import eatnrun.core.Entity;
import eatnrun.core.Face;
import eatnrun.core.Level;
import eatnrun.core.MoveableEntity;
import eatnrun.core.handler.CollisionHandler;
import eatnrun.core.handler.StepHandler;
import gui.Window;

public class Monster extends MoveableEntity implements CollisionHandler, StepHandler {
  private Face face;

  public Monster(Level level, int x, int y, Face face) {
    super(level, x, y, 40, 40, 5);
    this.face = face;
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/monster.png", x, y);
  }

  private void bounce() {
    switch (face) {
      case NORTH:
        face = Face.SOUTH;
        break;
      case EAST:
        face = Face.WEST;
        break;
      case SOUTH:
        face = Face.NORTH;
        break;
      case WEST:
        face = Face.EAST;
        break;
    }
  }

  @Override
  public void onCollide(Entity contact) {
    if (contact instanceof Block) {
      bounce();
    }
  }

  @Override
  public void onStep() {
    switch (face) {
      case NORTH:
        moveUp();
        break;
      case EAST:
        moveRight();
        break;
      case SOUTH:
        moveDown();
        break;
      case WEST:
        moveLeft();
        break;
    }
  }
}
