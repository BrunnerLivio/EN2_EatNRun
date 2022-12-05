package eatnrun;

import eatnrun.entities.Player;
import gui.Window;

public class EatNRunGame {
  private int width;
  private int height;
  private Player player;

  public EatNRunGame(int width, int height) {
    this.width = width;
    this.height = height;
    this.player = new Player(0, 0);
  }

  public void handleEvents(Window window) {
    if (window.isKeyPressed("up") || window.isKeyPressed("w")) {
      player.moveUp();
    }

    if (window.isKeyPressed("down") || window.isKeyPressed("s")) {
      player.moveDown();
    }

    if (window.isKeyPressed("right") || window.isKeyPressed("d")) {
      player.moveRight();
    }

    if (window.isKeyPressed("left") || window.isKeyPressed("a")) {
      player.moveLeft();
    }
  }

  public void step(Window window) {
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
