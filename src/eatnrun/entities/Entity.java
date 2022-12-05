package eatnrun.entities;

import gui.Window;

public abstract class Entity {

  protected int x;
  protected int y;
  protected int height;
  protected int width;

  public Entity(int x, int y, int width, int height) {
    this.x = x + width / 2;
    this.y = y + height / 2;
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

  abstract public void draw(Window window);

  public boolean collidesNorth(Entity other) {
    return y - height / 2 < other.y + other.height / 2;
  }

  public boolean collidesSouth(Entity other) {
    return y + height / 2 > other.y - other.height / 2;
  }

  public boolean intersectsWest(Entity other) {
    return x - width / 2 <= other.x + other.width / 2;
  }

  public boolean intersectsEast(Entity other) {
    return x + width / 2 >= other.x - other.width / 2;
  }

  public boolean intersects(Entity other, int xOffset, int yOffset) {
    return x + width / 2 > other.x + xOffset - other.width / 2 &&
        x - width / 2 < other.x + xOffset + other.width / 2 &&
        y - height / 2 < other.y + yOffset + other.height / 2 &&
        y + height / 2 > other.y + yOffset - height / 2;
  }
}