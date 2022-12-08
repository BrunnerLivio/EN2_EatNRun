package eatnrun.core;

import java.util.List;

import gui.Window;

/**
 * Represents an entity which has the ability to move
 */
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

  /**
   * Returns the X/Y position relative to the entity
   * where it would move to, depending on the given face
   */
  private int[] getNextStepOfFace(Face face) {
    int x = 0;
    int y = 0;

    switch (face) {
      case NORTH:
        y = getSpeed() * -1;
        break;
      case SOUTH:
        y = getSpeed();
        break;
      case WEST:
        x = getSpeed() * -1;
        break;
      case EAST:
        x = getSpeed();
        break;
    }

    return new int[] { x, y };
  }

  /**
   * Whether the entity can move to the given direction
   * @param face The direction it should check
   * @param entities All the entities which should be able to block this entity
   */
  public boolean canMove(Face face, List<Entity> blockingEntities) {
    int[] offsets = getNextStepOfFace(face);

    int xOffset = offsets[0];
    int yOffset = offsets[1];

    boolean intersects = false;
    int i = 0;
    while (!intersects && i < blockingEntities.size()) {
      intersects = blockingEntities.get(i).intersects(this, xOffset, yOffset);
      i++;
    }
    return !intersects;
  }

  abstract public void draw(Window window);

}
