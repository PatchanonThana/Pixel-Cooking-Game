package window.screen.menuScreen.menuButton.startButton;

import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import javax.sound.sampled.*;

import static java.awt.Cursor.HAND_CURSOR;


public class MenuStartButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(130,50);
    MenuStartButtonListener startButtonListener;
    private Clip clip;

    public MenuStartButton(MenuStartButtonListener startButtonListener){
        this.startButtonListener = startButtonListener;
        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        ImageIcon rawStart = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/startButton/startImage/startbutton.png"
                        )));
        Image scaledStart = rawStart.getImage().getScaledInstance(thisSize.width, thisSize.height, Image.SCALE_SMOOTH);
        Icon startImage = new ImageIcon(scaledStart);
        setIcon(startImage);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

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