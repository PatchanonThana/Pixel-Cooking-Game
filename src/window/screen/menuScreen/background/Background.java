package window.screen.menuScreen.background;


import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Background extends JPanel implements MenuListener {

    private final Image backgroundImage;
    private Dimension thisSize;

    public Background() {
        setBackground(Color.GRAY);
        backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/background/Main_screen_close_(1).png"
        ))).getImage();
    }

    @Override
    public void menuResized(Dimension size) {

        thisSize = new Dimension(size.width,size.height);

        setBounds(
                0,
                0,
                size.width,
                size.height
        );

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (thisSize != null && backgroundImage != null) {
            g.drawImage(backgroundImage,0,0,thisSize.width, thisSize.height, this);
        }
    }

}
