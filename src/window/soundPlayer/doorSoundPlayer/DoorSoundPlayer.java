package window.soundPlayer.doorSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class DoorSoundPlayer extends SoundPlayer {

    Clip clip;
    public DoorSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/doorSoundPlayer/dragon-studio-heavy-door-unlocking-515258.wav");
    }

    public void playSound() {
        playSound(clip,1000,0);
    }
}
