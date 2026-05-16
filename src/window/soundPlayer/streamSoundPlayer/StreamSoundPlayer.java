package window.soundPlayer.streamSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class StreamSoundPlayer extends SoundPlayer {
    Clip clipClock;

    public StreamSoundPlayer() {
        super();
        clipClock = getAudioStream("/window/soundPlayer/streamSoundPlayer/dragon-studio-clock-ticking-down-376897.wav");
    }

    public void playSound() {
        playSoundWithDing(clipClock,3000);
    }

}
