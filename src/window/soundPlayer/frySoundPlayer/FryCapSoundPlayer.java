package window.soundPlayer.frySoundPlayer;
import window.soundPlayer.SoundPlayer;
import javax.sound.sampled.Clip;

public class FryCapSoundPlayer extends SoundPlayer {

    Clip clip;

    public FryCapSoundPlayer() {
        super();
        clip = getAudioStream("/window/soundPlayer/frySoundPlayer/alex_jauk-food-cooking-in-oil-178795.wav");
    }

    public void playSound() {
        playSoundWithDing(clip,2000,0);
    }
}

