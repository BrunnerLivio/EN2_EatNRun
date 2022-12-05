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
  private List<Monster> monsters;

  public EatNRunGame(int width, int height) {
    this.width = width;
    this.height = height;
    this.player = new Player(40, 85);
    this.blocks = Arrays.asList(new Block(40, 40), new Block(120, 120), new Block(160, 120), new Block(240, 160), new Block(0, 160));
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
    window.setColor(0, 0, 0);
    window.setFontSize(40);
    player.draw(window);
    blocks.forEach(block -> block.draw(window));
    monsters.forEach(monster -> monster.draw(window));
    // window.drawStringCentered(scoreLeft + ":" + scoreRight, width * 0.5, PADDING
    // + 60);

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
