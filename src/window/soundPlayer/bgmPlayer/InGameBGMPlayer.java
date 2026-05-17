package window.soundPlayer.bgmPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

public class InGameBGMPlayer {

    private Clip clip;

    public InGameBGMPlayer() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(
                            getClass().getResource(
                                    "/window/soundPlayer/bgmPlayer/openmindaudio-cartoon-classic-background-music-bright-journey-short-preview-497390.wav"
                            )));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);

            FloatControl soundControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            soundControl.setValue(-10f);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void start() {
        if (clip.isRunning()) clip.stop();
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
    }

    public void destroy() {
        if (clip != null) {
            clip.stop();
            clip.flush();
            clip.close();
        }
    }
}
