package window.soundPlayer.buttonSoundPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

public class ButtonSoundPlayer {

    private Clip clip;

    public ButtonSoundPlayer() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(getClass().getResource(
                            "click-button-sound-2.wav"
                    ))
            );
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            FloatControl soundControl =  (FloatControl)(clip.getControl(FloatControl.Type.MASTER_GAIN));
            soundControl.setValue(6.0206f);
        }
        catch(Exception e) {
            e.getStackTrace();
            System.out.println(e.getMessage());

        }
    }
    public void playSound() {
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
