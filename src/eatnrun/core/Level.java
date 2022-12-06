package eatnrun.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import eatnrun.entities.Block;
import eatnrun.entities.Cake;
import eatnrun.entities.Finish;
import eatnrun.entities.Monster;
import eatnrun.entities.Player;
import gui.Window;

public class Level {

  private String[] mapData;
  private int levelNum;
  private List<Entity> entities;
  private GameController game;

  private final int GRID_SIZE = 40;

  public Level(GameController game, int levelNum) {
    this.levelNum = levelNum;
    this.game = game;
  }

  private static String[] loadTextFile(int level) {
    try {
      return Files.readAllLines(Paths.get("resources", "maps", level + ".txt")).toArray(new String[] {});
    } catch (IOException iox) {
      throw new RuntimeException(iox);
    }
  }

  private void load() {
    if (mapData == null) {
      this.mapData = loadTextFile(this.levelNum);
    }
  }

  private Entity getEntityFromChar(char character, int x, int y) {
    Entity entity = null;
    switch (character) {
      case '#':
        entity = new Block(this, x, y);
        break;
      case 'F':
        entity = new Finish(this, x, y);
        break;
      case 'E':
        entity = new Monster(this, x, y, 2, 0);
        break;
      case 'N':
        entity = new Monster(this, x, y, 0, -2);
        break;
      case 'S':
        entity = new Monster(this, x, y, 0, 2);
        break;
      case 'W':
        entity = new Monster(this, x, y, -2, 0);
        break;
      case 'C':
        entity = new Cake(this, x, y);
        break;
      case 'P':
        entity = new Player(this, x, y);
      default:
        break;
    }
    return entity;
  }

  public void removeEntity(Entity entity) {
    entities.remove(entity);
  }

  public GameController getGame() {
    return game;
  }

  public int getLevelNum() {
    return levelNum;
  }

  public List<Entity> getEntities() {
    return new ArrayList<>(entities);
  }

  public void finish() {
    game.finishLevel(levelNum);
  }

  public void draw(Window window) {
    entities.forEach(entity -> entity.draw(window));
  }

  public void start() {
    load();

    entities = new ArrayList<>();

    for (int row = 0; row < mapData.length; row++) {
      for (int column = 0; column < mapData[row].length(); column++) {
        char character = mapData[row].charAt(column);
        int x = column * GRID_SIZE;
        int y = row * GRID_SIZE;
        Entity entity = getEntityFromChar(character, x, y);
        if (entity != null) {
          entities.add(entity);
        }
      }
    }
  }
}
