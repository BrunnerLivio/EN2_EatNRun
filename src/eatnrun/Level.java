package eatnrun;

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

  private Player player;
  private Finish finish;
  private List<Block> blocks;
  private List<Monster> monsters;
  private List<Cake> cakes;

  private final int GRID_SIZE = 40;

  public Level(int levelNum) {
    this.levelNum = levelNum;

    this.blocks = new ArrayList<>();
    this.monsters = new ArrayList<>();
    this.cakes = new ArrayList<>();
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

  private void addEntityFromChar(char character, int row, int column) {
    int x = column * GRID_SIZE;
    int y = row * GRID_SIZE;
    switch (character) {
      case '#':
        blocks.add(new Block(x, y));
        break;
      case 'F':
        finish = new Finish(x, y);
        break;
      case 'E':
        monsters.add(new Monster(x, y, 2, 0));
        break;
      case 'N':
        monsters.add(new Monster(x, y, 0, -2));
        break;
      case 'C':
        cakes.add(new Cake(x, y));
        break;
      case 'P':
        player = new Player(x, y);
      default:
        break;
    }
  }

  public int getLevelNum() {
    return levelNum;
  }

  public Player getPlayer() {
    return player;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public List<Monster> getMonsters() {
    return monsters;
  }

  public List<Cake> getCakes() {
    return new ArrayList<>(cakes);
  }

  public Finish getFinish() {
    return finish;
  }

  public void removeCake(Cake cake) {
    this.cakes.remove(cake);
  }

  public void draw(Window window) {
    player.draw(window);
    finish.draw(window);
    blocks.forEach(block -> block.draw(window));
    monsters.forEach(monster -> monster.draw(window));
    cakes.forEach(cake -> cake.draw(window));
  }

  public void start() {
    load();

    for (int row = 0; row < mapData.length; row++) {
      for (int column = 0; column < mapData[row].length(); column++) {
        char character = mapData[row].charAt(column);
        addEntityFromChar(character, row, column);
      }
    }
  }
}
