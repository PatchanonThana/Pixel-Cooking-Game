package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;
import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MenuExitButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);
    private final List<ExitButtonSoundListener> exitButtonSoundListeners;

    public MenuExitButton(List<ExitButtonSoundListener> exitButtonSoundListener) {
        this.exitButtonSoundListeners = exitButtonSoundListener;
        setText("exit");

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            for (ExitButtonSoundListener listener : this.exitButtonSoundListeners) {
                listener.exitButtonClicked();
            }

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
