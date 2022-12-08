package eatnrun.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import eatnrun.core.handler.CollisionHandler;
import eatnrun.core.handler.EventHandler;
import eatnrun.core.handler.StepHandler;
import eatnrun.util.Sound;
import gui.Window;

/**
 * The Game controller is the engine of the game
 * 
 * - It makes sure to call handlers at the right time
 * - It keeps track of the current level
 * - It keeps track of the score
 * - It keeps track of the lives
 */
public class GameController {
  private int score = 0;
  private int lives = 5;
  private GameStatus status = GameStatus.RUNNING;
  private Level currentLevel;
  private List<Level> levels;
  private int windowWidth;

  public GameController(int windowWidth) {
    createLevels();
    startLevel(1);
    this.windowWidth = windowWidth;
  }

  private static int getNumberOfLevels() {
    try {
      return (int) Files.list(Paths.get("resources", "maps"))
          .filter(p -> p.toFile().getName().endsWith(".txt"))
          .count();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void createLevels() {
    this.levels = new ArrayList<>();

    int totalLevels = getNumberOfLevels();
    for (int i = 1; i < totalLevels + 1; i++) {
      Level level = new Level(this, i);
      levels.add(level);
    }
  }

  private void startLevel(int levelNum) {
    currentLevel = levels.get(levelNum - 1);
    currentLevel.start();
  }

  public void incrementScore() {
    score++;
    Sound.playSound(Sound.SLURP);
  }

  public void finishLevel(int currentLevel) {
    if (currentLevel + 1 > getNumberOfLevels()) {
      status = GameStatus.WON;
      Sound.playSound(Sound.SUCCESS);
    } else {
      Sound.playSound(Sound.NEW_LEVEL);
      startLevel(currentLevel + 1);
    }
  }

  public void removeLive() {
    if (lives > 1) {
      lives--;
      Sound.playSound(Sound.DIE);
    } else {
      status = GameStatus.LOST;
      Sound.playSound(Sound.GAME_OVER);
      return;
    }
  }

  // NOTE: This function is bascially the heart of my approach
  // =========================================================
  //
  // When I started with the project, I've realized that my program is
  // not very cohesive. For instance; the actions when e.g. the Player
  // collides with a Block were not handled within the Player class.
  //
  // I took inspiration from game engines such as Unity and wanted to
  // let an Entity class handle certain events. Therefore I came up
  // with this handler approach. I had to sacrifice performance due to
  // this, though my goal was to write cohesive and clean code, rather than
  // look out for performance.
  //
  // Though the beauty of this approach is that you'll find all the information
  // and actions of e.g. a Player within a Player class. Adding new entites with
  // different functionality is quite easy and readable.
  /**
   * Iterate through all the entities
   * 
   * If the entity implements one of the following interface(s), then call its
   * handler:
   * - CollisionHandler
   * - StepHandler
   * - EventHandler
   */
  public void callHandlers(Window window) {
    List<Entity> entities = currentLevel.getEntities();
    for (Entity entity1 : entities) {
      for (Entity entity2 : entities) {
        if (entity1 == entity2 || !(entity1 instanceof CollisionHandler)) {
          continue;
        }

        CollisionHandler target = (CollisionHandler) entity1;
        // There is a collision => Notify the entity
        if (target.intersects(entity2)) {
          target.onCollide(entity2);
        }
      }
      if (entity1 instanceof StepHandler) {
        StepHandler target = (StepHandler) entity1;
        target.onStep();
      }

      if (entity1 instanceof EventHandler) {
        EventHandler eventHandler = (EventHandler) entity1;
        eventHandler.handleEvents(window);
      }
    }
  }

  public void drawGame(Window window) {
    currentLevel.draw(window);
    window.setColor(255, 255, 255);
    window.setFontSize(20);
    window.drawStringCentered("Cakes: " + score + " Lives: " + lives + " Level: " + currentLevel.getLevelNum(),
        windowWidth * 0.5, 30);
  }

  public GameStatus getStatus() {
    return status;
  }
}
