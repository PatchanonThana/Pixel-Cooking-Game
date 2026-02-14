package window.soundPlayer.bgmPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

public class BGMPlayer {

    private Clip clip;

    public BGMPlayer() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("BGM.wav")));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            FloatControl soundControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            soundControl.setValue(-10f);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
        }
    }
}
