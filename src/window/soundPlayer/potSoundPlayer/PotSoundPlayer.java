package window.soundPlayer.potSoundPlayer;

import window.soundPlayer.SoundPlayer;

import javax.sound.sampled.Clip;

public class PotSoundPlayer extends SoundPlayer {

    Clip clip;

    public PotSoundPlayer() {
        clip = getAudioStream("/window/soundPlayer/potSoundPlayer/freesound_community-setting-cast-iron-on-stove-96654.wav");
    }

    public void playSound() {
        playSound(clip,1000,1000000);
    }
}
