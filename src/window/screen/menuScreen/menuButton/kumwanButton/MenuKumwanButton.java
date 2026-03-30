package window.screen.menuScreen.menuButton.kumwanButton;

import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MenuKumwanButton extends JButton implements MenuListener {

    private final Dimension thisSize = new Dimension(825, 150);

    public MenuKumwanButton() {

        // --------- รูป ---------
        ImageIcon raw = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/kumwanButton/kumwanImage/Kumwanbutton.png"
        )));
        Image scaled = raw.getImage().getScaledInstance(
                thisSize.width, thisSize.height, Image.SCALE_SMOOTH
        );
        setIcon(new ImageIcon(scaled));

        // --------- UI ---------
        setBorder(null);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);

        // --------- ปิดการคลิก ---------
        setEnabled(true);
        setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
                (int)(size.getWidth()/2 - thisSize.width/2.0),
                (int)(size.getHeight()/2 - thisSize.height/2.0) - 150,
                thisSize.width,
                thisSize.height
        );
    }
}