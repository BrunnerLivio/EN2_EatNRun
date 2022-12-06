package eatnrun.core.handler;

import eatnrun.core.Entity;

public interface CollisionHandler {
  void onCollide(Entity contact);

  boolean intersects(Entity other);
}
