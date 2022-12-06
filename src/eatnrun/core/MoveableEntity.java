package eatnrun.core;

import gui.Window;

public abstract class MoveableEntity extends Entity {

  private int speed;

  public MoveableEntity(Level level, int x, int y, int width, int height, int speed) {
    super(level, x, y, width, height);
    this.speed = speed;
  }

  private void move(int dx, int dy) {
    this.x += dx;
    this.y += dy;
  }

  public void moveUp() {
    move(0, speed * -1);
  }

  public void moveDown() {
    move(0, speed);
  }

  public void moveRight() {
    move(speed, 0);
  }

  public void moveLeft() {
    move(speed * -1, 0);
  }

  public int getSpeed() {
    return speed;
  }

  abstract public void draw(Window window);

}
