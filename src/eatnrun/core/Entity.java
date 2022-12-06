package eatnrun.core;

import gui.Window;

public abstract class Entity {

  protected int x;
  protected int y;
  protected int height;
  protected int width;
  private int initialX;
  private int initialY;

  public Entity(int x, int y, int width, int height) {
    this.x = x + width / 2;
    this.y = y + height / 2;
    this.initialX = this.x;
    this.initialY = this.y;
    this.height = height;
    this.width = width;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public void resetPosition() {
    x = initialX;
    y = initialY;
  }

  public boolean intersects(Entity other) {
    return intersects(other, 0, 0);
  }

  public boolean intersects(Entity other, int xOffset, int yOffset) {
    return x + width / 2 > other.x + xOffset - other.width / 2 &&
        x - width / 2 < other.x + xOffset + other.width / 2 &&
        y - height / 2 < other.y + yOffset + other.height / 2 &&
        y + height / 2 > other.y + yOffset - height / 2;
  }

  abstract public void draw(Window window);
}