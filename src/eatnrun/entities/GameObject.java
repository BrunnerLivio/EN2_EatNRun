package eatnrun.entities;

import gui.Window;

public abstract class GameObject {

  protected int x;
  protected int y;
  protected int height;
  protected int width;

  public GameObject(int x, int y) {
    this.x = x + width / 2;
    this.y = y + height / 2;
  }

  public GameObject(int x, int y, int width, int height) {
    this.x = x + width / 2;
    this.y = y + height / 2;
    this.height = height;
    this.width = width;
  }

  public void move(int dx, int dy) {
    this.x += dx;
    this.y += dy;
  }

  abstract public void draw(Window window);

  public boolean intersects(GameObject other) {
    return x - width / 2 < other.x + other.width / 2
        && x + width / 2 > other.x - other.width / 2
        && y - height / 2 < other.y + other.height / 2
        && y + height / 2 > other.y - other.height / 2;
  }
}