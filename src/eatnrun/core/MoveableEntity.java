package eatnrun.core;

import java.util.List;

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

  private int[] faceToCoordinates(Face face) {
    int xOffset = 0;
    int yOffset = 0;

    switch (face) {
      case NORTH:
        yOffset = getSpeed() * -1;
        break;
      case SOUTH:
        yOffset = getSpeed();
        break;
      case EAST:
        xOffset = getSpeed();
        break;
      case WEST:
        xOffset = getSpeed() * -1;
        break;
    }

    return new int[] { xOffset, yOffset };
  }

  public boolean canMove(List<Entity> entities, Face face) {
    int[] offsets = faceToCoordinates(face);

    int xOffset = offsets[0];
    int yOffset = offsets[1];

    boolean intersects = false;
    int i = 0;
    while (!intersects && i < entities.size()) {
      intersects = entities.get(i).intersects(this, xOffset, yOffset);
      i++;
    }
    return !intersects;
  }

  abstract public void draw(Window window);

}
