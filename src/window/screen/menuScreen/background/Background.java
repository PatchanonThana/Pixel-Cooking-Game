package window.screen.menuScreen.background;


import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel implements MenuListener {

    public Background() {
        setBackground(Color.GRAY);
    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
                0,
                0,
                size.width,
                size.height
        );
    }

}
