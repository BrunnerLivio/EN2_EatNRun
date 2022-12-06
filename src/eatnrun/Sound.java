package eatnrun;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;

/**
 * Funktionalität um Audio-Dateien abzuspielen.
 */
public final class Sound {
  public static final String SLURP = "resources/sounds/slurp.wav";
  public static final String SUCCESS = "resources/sounds/success.wav";
  public static final String GAME_OVER = "resources/sounds/gameover.wav";
  public static final String DIE = "resources/sounds/die.wav";
  public static final String NEW_LEVEL = "resources/sounds/new_level.wav";

  private static final LinkedBlockingQueue<String> clips = new LinkedBlockingQueue<>();
  private static final Thread worker = new Thread(() -> runWorker());

  static {
    worker.setDaemon(true);
    worker.start();
  }

  private Sound() {
    // Klasse soll nicht instanzierbar sein.
  }

  private static void runWorker() {
    while (true) {
      try {
        String fileName = clips.take();
        CountDownLatch syncLatch = new CountDownLatch(1);
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(new File(fileName))) {
          Clip clip = AudioSystem.getClip();

          // Listener which allow method return once sound is completed
          clip.addLineListener(e -> {
            if (e.getType() == LineEvent.Type.STOP) {
              syncLatch.countDown();
            }
          });

          clip.open(stream);
          clip.start();
        }
        syncLatch.await();
      } catch (InterruptedException e) {
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Spiel die angegebene Audio Datei ab. Diese Methode blockiert nicht.
   * 
   * @param fileName Name der Audio Datei.
   */
  public static synchronized void playSound(String fileName) {
    clips.clear();
    clips.offer(fileName);
  }
}
