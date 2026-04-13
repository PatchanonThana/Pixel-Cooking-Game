package window.screen.menuScreen.menuButton.startButton;

import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;
import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class MenuStartButton extends JButton implements MenuListener {

    private final MenuStartButtonListener startButtonListener;

    private final Image rawStartImage;
    private final Image rawHoverStartImage;

    public MenuStartButton(MenuStartButtonListener startButtonListener){
        this.startButtonListener = startButtonListener;

        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        rawStartImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/startButton/startImage/startbutton.png"
        ))).getImage();

        rawHoverStartImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/startButton/startImage/startbutton-hover.png"
        ))).getImage();

        setMargin(new Insets(0, 0, 0, 0));
        setBorder(null);
        setRolloverEnabled(true);

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            this.startButtonListener.menuStartButtonClicked();
        });
    }

    @Override
    public void menuResized(Dimension size) {

        int width = (int)(size.getWidth() * 0.25);
        int height = (int)(width * 0.25);

        int centerY = (int)(size.getHeight() / 2 - height / 2);

        int x = (int)(size.getWidth() / 2 - width / 2);
        int y = centerY;

        setBounds(x, y, width, height);

        Image scaledStart = rawStartImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledStart));

        Image scaledHover = rawHoverStartImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setRolloverIcon(new ImageIcon(scaledHover));
    }
}