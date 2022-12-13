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

/**
 * Represents one level.
 * This class stores all the entities which should be rendered by this level
 */
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
    switch (character) {
      case '#':
        return new Block(this, x, y);
      case 'F':
        return new Finish(this, x, y);
      case 'E':
        return new Monster(this, x, y, Face.EAST);
      case 'N':
        return new Monster(this, x, y, Face.NORTH);
      case 'S':
        return new Monster(this, x, y, Face.SOUTH);
      case 'W':
        return new Monster(this, x, y, Face.WEST);
      case 'C':
        return new Cake(this, x, y);
      case 'P':
        return new Player(this, x, y);
      default:
        return null;
    }
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
    for(Entity entity: entities) {
      entity.draw(window);
    }
  }

  public void start() {
    load();

    entities = new ArrayList<>();

    // Go through all the rows (lines) of the map data
    for (int row = 0; row < mapData.length; row++) {

      // Go through all the columns (characters) of the map data
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
