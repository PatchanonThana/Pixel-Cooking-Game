package window.screen.menuScreen.menuButton.startButton;

import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;

import static java.awt.Cursor.HAND_CURSOR;


public class MenuStartButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);
    MenuStartButtonListener startButtonListener;
    private Clip clip;

    public MenuStartButton(MenuStartButtonListener startButtonListener){
        this.startButtonListener = startButtonListener;
        setText("start");
        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
                    buttonSoundPlayer.playSound();
                    this.startButtonListener.menuStartButtonClicked();
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