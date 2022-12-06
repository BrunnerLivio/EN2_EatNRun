package eatnrun.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import eatnrun.core.Entity;
import eatnrun.core.Level;
import eatnrun.core.MoveableEntity;
import eatnrun.core.handler.CollisionHandler;
import eatnrun.core.handler.EventHandler;
import gui.Window;

public class Player extends MoveableEntity implements EventHandler, CollisionHandler {
  public Player(Level level, int x, int y) {
    super(level, x, y, 40, 40, 5);
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

    Stream<Block> blocks = level.getEntities().stream().filter(e -> e instanceof Block).map(e -> (Block) e);

    // Check whether it would collide with any block
    // If it would, then don't perform the action
    if (pressedUp && !blocks.anyMatch(block -> block.intersects(this, 0, getSpeed() * -1))) {
      moveUp();
    }

    if (pressedDown && !blocks.anyMatch(block -> block.intersects(this, 0, getSpeed()))) {
      moveDown();
    }

    if (pressedLeft && !blocks.anyMatch(block -> block.intersects(this, getSpeed() * -1, 0))) {
      moveLeft();
    }

    if (pressedRight && !blocks.anyMatch(block -> block.intersects(this, getSpeed(), 0))) {
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
