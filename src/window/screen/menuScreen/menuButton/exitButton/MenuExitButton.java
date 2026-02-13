package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;

public class MenuExitButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);

    public MenuExitButton() {

        setText("exit");
        addActionListener(e -> System.exit(0));

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
