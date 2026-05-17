package window.soundPlayer.correctSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class CorrectSoundPlayer extends SoundPlayer {

    Clip clip;

    public CorrectSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/correctSoundPlayer/universfield-game-bonus-02-294436.wav");
    }

    public void playSound() {
        playSound(clip,1000,0);
    }

}
