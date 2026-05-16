package window.soundPlayer.boilSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class BoilSoundPlayer extends SoundPlayer {

    Clip clip;

    public BoilSoundPlayer() {
        super();
        clip = getAudioStream("/window/soundPlayer/boilSoundPlayer/freesound_community-boiling-water-23592.wav");
    }

    public void playSound() {
        playSoundWithDing(clip,1500,60000000);
    }
}
