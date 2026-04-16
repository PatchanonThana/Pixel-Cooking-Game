package window.screen.menuScreen.menuButton.kumwanButton;

import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuKumwanButton extends JLabel implements MenuListener {

    private final Image rawImage;

    public MenuKumwanButton() {

        rawImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/kumwanButton/kumwanImage/Kumwanbutton.png"
        ))).getImage();

        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);

        setOpaque(false);
    }

    @Override
    public void menuResized(Dimension size) {

        int width = (int)(size.getWidth() * 0.4);
        int height = (int)(width * 0.18);

        int spacing = (int)(size.getHeight() * 0.12);
        int centerY = (int)(size.getHeight() / 2 - height / 2);

        int x = (int)(size.getWidth() / 2 - width / 2);
        int y = centerY - spacing;

        setBounds(x, y, width, height);

        Image scaled = rawImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaled));
    }
}