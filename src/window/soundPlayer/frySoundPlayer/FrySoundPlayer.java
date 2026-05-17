package window.soundPlayer.frySoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.Objects;

public class FrySoundPlayer extends SoundPlayer {

    Clip clip;

    public FrySoundPlayer() {
        super();
        clip = getAudioStream("/window/soundPlayer/frySoundPlayer/alex_jauk-food-cooking-in-oil-178795.wav");
    }

    public void playSound() {
        playSound(clip,2000,0);
    }
}

