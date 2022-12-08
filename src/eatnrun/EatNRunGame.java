package eatnrun;
import eatnrun.core.GameController;
import gui.Window;

public class EatNRunGame {

  private static int WINDOW_WIDTH = 800;
  private static int WINDOW_HEIGHT = 600;

  public static void main(String[] args) {

    GameController game = new GameController(WINDOW_HEIGHT);

    Window window = new Window("Eat'n'Run", WINDOW_WIDTH, WINDOW_HEIGHT);
    window.open();

    while (window.isOpen()) {

      switch (game.getStatus()) {
        case RUNNING:
          game.callHandlers(window);
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
