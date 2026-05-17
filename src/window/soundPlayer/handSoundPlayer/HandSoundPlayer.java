package window.soundPlayer.handSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class HandSoundPlayer extends SoundPlayer {

    Clip clip;

    public HandSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/handSoundPlayer/freesound_community-cloth-rustle-1-30053.wav");
        FloatControl soundControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        soundControl.setValue(6f);
    }
    public void playSound() {
        playSound(clip,1000,0);
    }
}
