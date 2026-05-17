package window.soundPlayer.incorrectSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class IncorrectSoundPlayer extends SoundPlayer {

    Clip clip;

    public IncorrectSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/incorrectSoundPlayer/dragon-studio-thud-sound-effect-405470.wav");
    }

    public void playSound() {
        playSound(clip,1000,0);
    }
}
