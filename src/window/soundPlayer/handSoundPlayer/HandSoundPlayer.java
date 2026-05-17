package window.soundPlayer.handSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class HandSoundPlayer extends SoundPlayer {

    Clip clip;

    public HandSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/handSoundPlayer/freesound_community-cloth-rustle-1-30053.wav");
    }
    public void playSound() {
        playSound(clip,1000,0);
    }
}
