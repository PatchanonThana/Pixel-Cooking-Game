package window.soundPlayer.sugarSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class SugarSoundPlayer extends SoundPlayer {

    Clip clip;

    public SugarSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/sugarSoundPlayer/dragon-studio-sand-transition-451861.wav");
    }

    public void playSound() {
        playSound(clip,1000,2000000);
    }
}
