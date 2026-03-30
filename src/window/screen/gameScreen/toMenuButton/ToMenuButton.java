package window.screen.gameScreen.toMenuButton;

import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.gameScreen.GameScreenListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class ToMenuButton extends JButton implements GameScreenListener {

    final private Dimension thisSize = new Dimension(150,50);
    final private ToMenuButtonListener toMenuButtonListener;

    public ToMenuButton(ToMenuButtonListener toMenuButtonListener) {
        this.toMenuButtonListener = toMenuButtonListener;
        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        ImageIcon rawBack = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/gameScreen/toMenuButton/toMenuImage/backbutton.png"
        )));
        Image scaledBack = rawBack.getImage().getScaledInstance(thisSize.width,thisSize.height,Image.SCALE_DEFAULT);
        Icon back = new ImageIcon(scaledBack);
        setIcon(back);

        ImageIcon rawHoverBack = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/gameScreen/toMenuButton/toMenuImage/backbutton-Hover.png"
        )));
        Image scaledHoverBack = rawHoverBack.getImage().getScaledInstance(thisSize.width,thisSize.height,Image.SCALE_DEFAULT);
        Icon hoverBack = new ImageIcon(scaledHoverBack);
        setRolloverIcon(hoverBack);

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

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
                30,
                thisSize.width,
                thisSize.height
        );
    }
}
