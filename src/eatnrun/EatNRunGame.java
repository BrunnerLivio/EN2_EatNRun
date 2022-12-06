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

public class EatNRunGame {

  private static int WINDOW_WIDTH = 800;
  private static int WINDOW_HEIGHT = 600;

  private int lives = 4;
  private int score = 0;

  private GameStatus status = GameStatus.RUNNING;

  private Level currentLevel;

  private List<Level> levels;

  public EatNRunGame() {
    this.levels = new ArrayList<>();

    int totalLevels = getNumberOfLevels();
    for (int i = 1; i < totalLevels + 1; i++) {
      Level level = new Level(i);
      levels.add(level);
    }

    startLevel(1);
  }

  public void startLevel(int levelNum) {
    currentLevel = levels.get(levelNum - 1);
    currentLevel.start();
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

  public void handleEvents(Window window) {
    boolean pressedUp = window.isKeyPressed("up") || window.isKeyPressed("w");
    boolean pressedDown = window.isKeyPressed("down") || window.isKeyPressed("s");
    boolean pressedRight = window.isKeyPressed("right") || window.isKeyPressed("d");
    boolean pressedLeft = window.isKeyPressed("left") || window.isKeyPressed("a");

    Player player = currentLevel.getPlayer();
    List<Block> blocks = currentLevel.getBlocks();

    if (pressedUp && !blocks.stream().anyMatch(block -> block.intersects(player, 0, player.getSpeed() * -1))) {
      player.moveUp();
    }

    if (pressedDown && !blocks.stream().anyMatch(block -> block.intersects(player, 0, player.getSpeed()))) {
      player.moveDown();
    }

    if (pressedLeft && !blocks.stream().anyMatch(block -> block.intersects(player, player.getSpeed() * -1, 0))) {
      player.moveLeft();
    }

    if (pressedRight && !blocks.stream().anyMatch(block -> block.intersects(player, player.getSpeed(), 0))) {
      player.moveRight();
    }
  }

  public void step(Window window) {
    List<Monster> monsters = currentLevel.getMonsters();
    List<Cake> cakes = currentLevel.getCakes();
    List<Block> blocks = currentLevel.getBlocks();
    Player player = currentLevel.getPlayer();
    Finish finish = currentLevel.getFinish();

    for (Monster monster : monsters) {
      if (blocks.stream().anyMatch(block -> block.intersects(monster))) {
        monster.bounce();
      }

      if (monster.intersects(player)) {
        if (lives > 1) {
          lives--;
          Sound.playSound(Sound.DIE);
        } else {
          status = GameStatus.LOST;
          Sound.playSound(Sound.GAME_OVER);
          return;
        }

        player.resetPosition();
      }

      monster.step();
    }

    for (Cake cake : cakes) {
      if (cake.intersects(player)) {
        currentLevel.removeCake(cake);
        Sound.playSound(Sound.SLURP);
        score++;
      }
    }

    if (finish.intersects(player)) {
      if (currentLevel.getLevelNum() + 1 > getNumberOfLevels()) {
        status = GameStatus.WON;
        Sound.playSound(Sound.SUCCESS);
        return;
      } else {
        Sound.playSound(Sound.NEW_LEVEL);
        startLevel(currentLevel.getLevelNum() + 1);
      }
    }
  }

  public void drawGame(Window window) {
    currentLevel.draw(window);
    window.setColor(255, 255, 255);
    window.setFontSize(20);
    window.drawStringCentered("Cakes: " + score + " Lives: " + lives + " Level: " + currentLevel.getLevelNum(),
        WINDOW_WIDTH * 0.5, 30);
  }

  public GameStatus getStatus() {
    return status;
  }

  public static void main(String[] args) {

    EatNRunGame game = new EatNRunGame();

    Window window = new Window("Eat'n'Run", WINDOW_WIDTH, WINDOW_HEIGHT);
    window.open();

    while (window.isOpen()) {

      switch (game.getStatus()) {
        case RUNNING:
          game.handleEvents(window);
          game.step(window);
          game.drawGame(window);
          break;
        case WON:
          window.setColor(0, 0, 0);
          window.setFontSize(40);
          window.drawStringCentered("You Won!", WINDOW_WIDTH * 0.5, WINDOW_HEIGHT * 0.5);
          break;
        case LOST:
          window.setColor(0, 0, 0);
          window.setFontSize(40);
          window.drawStringCentered("You Lost!", WINDOW_WIDTH * 0.5, WINDOW_HEIGHT * 0.5);
          break;
      }

      window.refreshAndClear(20);
    }
  }
}
