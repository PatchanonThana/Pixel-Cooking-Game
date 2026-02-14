package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;
import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static java.awt.Cursor.HAND_CURSOR;

public class MenuExitButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);
    private final List<ExitButtonListener> exitButtonListeners;

    public MenuExitButton(List<ExitButtonListener> exitButtonSoundListener) {
        this.exitButtonListeners = exitButtonSoundListener;
        setText("exit");
        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            for (ExitButtonListener listener : this.exitButtonListeners) {
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
