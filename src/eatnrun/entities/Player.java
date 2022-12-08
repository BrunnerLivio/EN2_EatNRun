package eatnrun.entities;

import gui.Window;

import java.util.ArrayList;
import java.util.List;

import eatnrun.core.Entity;
import eatnrun.core.Face;
import eatnrun.core.Level;
import eatnrun.core.MoveableEntity;
import eatnrun.core.handler.CollisionHandler;
import eatnrun.core.handler.EventHandler;

public class Player extends MoveableEntity implements EventHandler, CollisionHandler {
  public Player(Level level, int x, int y) {
    super(level, x, y, 40, 40, 5);
  }

  private List<Entity> getBlocks() {
    List<Entity> blocks = new ArrayList<>();

    for (Entity entity : level.getEntities()) {
      if (entity instanceof Block) {
        blocks.add((Entity) entity);
      }
    }

    return blocks;
  }

  @Override
  public void draw(Window window) {
    window.drawImageCentered("resources/images/hero.png", x, y);
  }

  @Override
  public void handleEvents(Window window, Level level) {
    boolean pressedUp = window.isKeyPressed("up") || window.isKeyPressed("w");
    boolean pressedDown = window.isKeyPressed("down") || window.isKeyPressed("s");
    boolean pressedRight = window.isKeyPressed("right") || window.isKeyPressed("d");
    boolean pressedLeft = window.isKeyPressed("left") || window.isKeyPressed("a");

    List<Entity> blocks = getBlocks();

    if (pressedUp && canMove(blocks, Face.NORTH)) {
      moveUp();
    }

    if (pressedDown && canMove(blocks, Face.SOUTH)) {
      moveDown();
    }

    if (pressedLeft && canMove(blocks, Face.WEST)) {
      moveLeft();
    }

    if (pressedRight && canMove(blocks, Face.EAST)) {
      moveRight();
    }
  }

  @Override
  public void onCollide(Entity contact) {
    if (contact instanceof Cake) {
      contact.destroy();
      level.getGame().incrementScore();
    }

    if (contact instanceof Finish) {
      level.finish();
    }

    if (contact instanceof Monster) {
      level.getGame().removeLive();
      resetPosition();
    }
  }
}
