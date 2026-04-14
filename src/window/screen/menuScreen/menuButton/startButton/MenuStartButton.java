package window.screen.menuScreen.menuButton.startButton;

import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class MenuStartButton extends JButton implements MenuListener {

    private final Dimension thisSize = new Dimension(600, 150);
    private final MenuStartButtonListener startButtonListener;

    public MenuStartButton(MenuStartButtonListener startButtonListener){
        this.startButtonListener = startButtonListener;

        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        // --------- Img ---------
        ImageIcon rawStart = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/startButton/startImage/startbutton.png"
        )));
        Image scaledStart = rawStart.getImage().getScaledInstance(
                thisSize.width, thisSize.height, Image.SCALE_SMOOTH
        );
        setIcon(new ImageIcon(scaledStart));

        // --------- Hover ---------
        ImageIcon rawHoverStart = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/startButton/startImage/startbutton-hover.png"
        )));
        Image scaledHoverStart = rawHoverStart.getImage().getScaledInstance(
                thisSize.width, thisSize.height, Image.SCALE_SMOOTH
        );
        setRolloverIcon(new ImageIcon(scaledHoverStart));

        // --------- FIX ---------
        setMargin(new Insets(0, 0, 0, 0));
        setBorder(null);
        setRolloverEnabled(true);

        setSize(thisSize);
        setPreferredSize(thisSize);
        setMinimumSize(thisSize);
        setMaximumSize(thisSize);

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // --------- UI ---------
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        // --------- Sound ---------
        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            this.startButtonListener.menuStartButtonClicked();
        });
    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
                (int)(size.getWidth()/2 - thisSize.width/2.0),
                (int)(size.getHeight()/2 - thisSize.height/2.0),
                thisSize.width,
                thisSize.height
        );
    }
}