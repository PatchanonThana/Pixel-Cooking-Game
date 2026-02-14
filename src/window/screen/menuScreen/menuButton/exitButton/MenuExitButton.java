package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;
import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;

import javax.swing.*;
import java.awt.*;

public class MenuExitButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);
    private final ExitButtonSoundListener exitButtonSoundListener;

    public MenuExitButton(ExitButtonSoundListener exitButtonSoundListener) {
        this.exitButtonSoundListener = exitButtonSoundListener;
        setText("exit");

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            this.exitButtonSoundListener.exitButtonClicked();

        });

    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
               (int) (size.getWidth()/2 - thisSize.width/2.0),
                (int) (size.getHeight()/2 - thisSize.height/2.0) + 100,
                thisSize.width,
                thisSize.height
        );
    }


}
