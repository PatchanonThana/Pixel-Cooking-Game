package window.soundPlayer.frySoundPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

public class FrySoundPlayer {
    Clip clip;
    public FrySoundPlayer() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(
                    "/window/soundPlayer/frySoundPlayer/alex_jauk-food-cooking-in-oil-178795.wav"
            )));
            clip = AudioSystem.getClip();
            clip.open(audioStream);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void playSound() {
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                if (clip.isRunning()) clip.stop();
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();

    }
}
