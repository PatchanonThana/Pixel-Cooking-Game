package window.screen.menuScreen.menuButton.startButton;

import window.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import javax.sound.sampled.*;


public class MenuStartButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);
    MenuStartButtonListener startButtonListener;
    private Clip clip;

    public MenuStartButton(MenuStartButtonListener startButtonListener){
        this.startButtonListener = startButtonListener;
        setText("start");

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
                    this.startButtonListener.menuStartButtonClicked();
                    buttonSoundPlayer.playSound();
                }
        );
    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
                (int) (size.getWidth()/2 - thisSize.width/2.0),
                (int) (size.getHeight()/2 - thisSize.height/2.0) ,
                thisSize.width,
                thisSize.height
        );
    }
}