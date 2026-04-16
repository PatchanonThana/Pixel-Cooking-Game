package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;
import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class MenuExitButton extends JButton implements MenuListener {

    private final List<ExitButtonListener> exitButtonListeners;

    private final Image rawExitImage;
    private final Image rawHoverExitImage;

    public MenuExitButton(List<ExitButtonListener> exitButtonSoundListener) {
        this.exitButtonListeners = exitButtonSoundListener;

        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        rawExitImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/exitButton/exitImage/quitbutton.png"
        ))).getImage();

        rawHoverExitImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/exitButton/exitImage/quitbutton-Hover.png"
        ))).getImage();

        setMargin(new Insets(0, 0, 0, 0));
        setBorder(null);
        setRolloverEnabled(true);

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);

        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            for (ExitButtonListener listener : exitButtonListeners) {
                listener.exitButtonClicked();
            }
        });
    }

    @Override
    public void menuResized(Dimension size) {

        int width = (int)(size.getWidth() * 0.25);
        int height = (int)(width * 0.25);

        int spacing = (int)(size.getHeight() * 0.12);
        int centerY = (int)(size.getHeight() / 2 - height / 2);

        int x = (int)(size.getWidth() / 2 - width / 2);
        int y = centerY + spacing;

        setBounds(x, y, width, height);

        Image scaledExit = rawExitImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledExit));

        Image scaledHover = rawHoverExitImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setRolloverIcon(new ImageIcon(scaledHover));
    }
}