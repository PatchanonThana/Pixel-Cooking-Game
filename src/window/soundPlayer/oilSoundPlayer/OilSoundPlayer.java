package window.soundPlayer.oilSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class OilSoundPlayer extends SoundPlayer {

    Clip clip;

    public OilSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/oilSoundPlayer/alex_jauk-pouring-bubbling-liquid-sound-447145.wav");
    }

    public void playSound() {
        playSound(clip,1000,2000000);
    }
}
