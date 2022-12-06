package eatnrun;

import java.util.Arrays;
import java.util.List;

import eatnrun.entities.Block;
import eatnrun.entities.Monster;
import eatnrun.entities.Player;
import gui.Window;

public class EatNRunGame {
  private int width;
  private int height;
  private Player player;
  private List<Block> blocks;
  private final int GRID_SIZE = 40;
  private List<Monster> monsters;
  private int lives = 3;
  private int cakes = 0;
  private int level = 0;

  public EatNRunGame(int width, int height) {
    this.width = width;
    this.height = height;
    this.player = new Player(GRID_SIZE, GRID_SIZE * 2);
    this.blocks = Arrays.asList(new Block(GRID_SIZE, GRID_SIZE), new Block(GRID_SIZE * 3, GRID_SIZE * 3),
        new Block(GRID_SIZE * 4, GRID_SIZE * 3), new Block(GRID_SIZE * 5, GRID_SIZE * 4),
        new Block(0, GRID_SIZE * 4));
    this.monsters = Arrays.asList(new Monster(160, 160, 5, 0));
  }

  public void handleEvents(Window window) {
    boolean pressedUp = window.isKeyPressed("up") || window.isKeyPressed("w");
    boolean pressedDown = window.isKeyPressed("down") || window.isKeyPressed("s");
    boolean pressedRight = window.isKeyPressed("right") || window.isKeyPressed("d");
    boolean pressedLeft = window.isKeyPressed("left") || window.isKeyPressed("a");

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
    monsters.forEach(monster -> {
      if (blocks.stream().anyMatch(block -> block.intersects(monster))) {
        monster.bounce();
      }

      if (monster.intersects(player)) {
        lives--;
        player.resetPosition();
      }

      monster.step();
    });

    // ball.step();
    // if (west.intersects(ball)) {
    // System.out.println("kol west");
    // ball.reset(window);
    // scoreRight++;
    // }
    // if (east.intersects(ball)) {
    // System.out.println("kol east");
    // ball.reset(window);
    // scoreLeft++;
    // }
    // if (south.intersects(ball)) {
    // System.out.println("kol south");
    // ball.bounceOfVertical();
    // }
    // if (north.intersects(ball)) {
    // System.out.println("kol north");
    // ball.bounceOfVertical();
    // }

    // if (playerLeft.intersects(ball)) {
    // ball.bounceOfHorizontal();
    // }

    // if (playerRight.intersects(ball)) {
    // ball.bounceOfHorizontal();
    // }
  }

  public void drawGame(Window window) {
    player.draw(window);
    blocks.forEach(block -> block.draw(window));
    monsters.forEach(monster -> monster.draw(window));
    window.setColor(0, 0, 0);
    window.setFontSize(20);
    window.drawStringCentered("Cakes: " + cakes + " Lives: " + lives + " Level: " + level, width * 0.5, 30);

    // window.setColor(255, 0, 0);
    // north.draw(window);
    // east.draw(window);
    // south.draw(window);
    // west.draw(window);

    // window.setColor(0, 0, 0);
    // playerLeft.draw(window);
    // playerRight.draw(window);
    // ball.draw(window);

  }
}
