package window.screen.gameScreen.toMenuButton;

import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.gameScreen.GameScreenListener;

import javax.swing.*;
import java.awt.*;

public class ToMenuButton extends JButton implements GameScreenListener {

    final private Dimension thisSize = new Dimension(100,30);
    final private ToMenuButtonListener toMenuButtonListener;

    public ToMenuButton(ToMenuButtonListener toMenuButtonListener) {
        this.toMenuButtonListener = toMenuButtonListener;
        setText("to menu");

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            this.toMenuButtonListener.gameToMenuButtonClicked();
        }
        );
    }

    @Override
    public void gameScreenResized(Dimension size) {
        setBounds(
                30,
                50,
                thisSize.width,
                thisSize.height
        );
    }
}
