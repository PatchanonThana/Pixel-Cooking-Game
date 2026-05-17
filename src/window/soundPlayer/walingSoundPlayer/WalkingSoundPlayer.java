package window.soundPlayer.walingSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class WalkingSoundPlayer extends SoundPlayer {

    Clip clip;

    public WalkingSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/walingSoundPlayer/dragon-studio-footsteps-on-wood-397989.wav");
    }

    public void playSound() {
        playSound(clip,10000,1000000);
    }

    public void stop() {
        if (clip.isRunning()) clip.stop();
    }

}
