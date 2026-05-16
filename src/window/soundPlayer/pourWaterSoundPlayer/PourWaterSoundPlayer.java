package window.soundPlayer.pourWaterSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class PourWaterSoundPlayer extends SoundPlayer {

    Clip clip;

    public PourWaterSoundPlayer() {
        clip =getAudioStream("/window/soundPlayer/pourWaterSoundPlayer/universfield-fill-water-192164.wav");
    }
    public void playSound() {
        playSound(clip,1000,0);
    }
}
