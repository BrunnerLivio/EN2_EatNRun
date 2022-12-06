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

  public GameStatus getStatus() {
    return status;
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

  public void handleEvents(Window window) {
    currentLevel.getEntities()
        .stream()
        .filter(e -> e instanceof EventHandler)
        .map(e -> (EventHandler) e)
        .forEach(e -> e.handleEvents(window, currentLevel));
  }

  public void step(Window window) {
    List<Entity> entities = currentLevel.getEntities();
    for (Entity entity1 : entities) {

      for (Entity entity2 : entities) {
        if (entity1 == entity2) {
          continue;
        }

        if (!(entity1 instanceof CollisionHandler)) {
          continue;
        }
        CollisionHandler target = (CollisionHandler) entity1;
        if (target.intersects(entity2)) {
          target.onCollide(entity2);
        }
      }
      if (entity1 instanceof StepHandler) {
        StepHandler target = (StepHandler) entity1;
        target.onStep();
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

}
