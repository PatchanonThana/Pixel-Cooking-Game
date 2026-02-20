package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;
import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class MenuExitButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(325,110);
    private final List<ExitButtonListener> exitButtonListeners;

    public MenuExitButton(List<ExitButtonListener> exitButtonSoundListener) {
        this.exitButtonListeners = exitButtonSoundListener;
        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        ImageIcon rawExit = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/exitButton/exitImage/quitbutton.png"
        )));
        Image scaledExit = rawExit.getImage().getScaledInstance(thisSize.width, thisSize.height, Image.SCALE_SMOOTH);
        Icon exitImage = new ImageIcon(scaledExit);
        setIcon(exitImage);


        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);

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
