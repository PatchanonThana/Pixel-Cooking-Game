package window.soundPlayer.DoorBellSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class DoorBellSoundPlayer extends SoundPlayer {

    Clip clip;

    public DoorBellSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/DoorBellSoundPlayer/freesound_community-shop-door-bell-6405.wav");
    }

    public void playSound() {
        playSound(clip,1200,100000);
    }
}
