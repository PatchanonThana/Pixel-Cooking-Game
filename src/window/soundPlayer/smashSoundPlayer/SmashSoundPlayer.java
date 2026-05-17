package window.soundPlayer.smashSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class SmashSoundPlayer extends SoundPlayer {

    Clip clip;

    public SmashSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/smashSoundPlayer/freesound_community-table-smash-47690.wav");
    }

    public void playSound() {
        playSound(clip,1000,0);
    }

}
