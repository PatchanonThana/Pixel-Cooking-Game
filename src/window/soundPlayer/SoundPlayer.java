package window.soundPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.util.Objects;

public class SoundPlayer {

    protected   Clip clipDing =
            getAudioStream(
                    "/window/soundPlayer/DingSound/freesound_community-microwave-ding-104123.wav"
            );


    public SoundPlayer() {
        FloatControl soundControl = (FloatControl) clipDing.getControl(FloatControl.Type.MASTER_GAIN);
        soundControl.setValue(6f);
    }

    public void playSoundWithDing(Clip clip, int time, int startPosition) {
        if (clip.isRunning()) clip.stop();
        clip.setMicrosecondPosition(startPosition);
        clip.start();

        if (clipDing.isRunning()) clipDing.stop();
        clipDing.setFramePosition(0);

        new Thread(() -> {
            try {
                Thread.sleep(time);
                if (clip.isRunning()) clip.stop();
                clipDing.start();
                Thread.sleep(1000);
                if (clipDing.isRunning()) clipDing.stop();
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();

    }

    public void playSound(Clip clip, int time, int startPosition) {
        if (clip.isRunning()) clip.stop();
        clip.setMicrosecondPosition(startPosition);
        clip.start();

        new Thread(() -> {
            try {
                Thread.sleep(time);
                if (clip.isRunning()) clip.stop();
            }
            catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    public Clip getAudioStream(String path) {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(
                    Objects.requireNonNull(
                            getClass().getResource(path))
            );
            Clip clipIn = AudioSystem.getClip();
            clipIn.open(audioStream);
            return clipIn;

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
