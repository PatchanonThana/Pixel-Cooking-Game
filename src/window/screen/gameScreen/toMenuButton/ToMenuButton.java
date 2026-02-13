package window.screen.gameScreen.toMenuButton;

import window.screen.gameScreen.GameScreen;
import window.screen.gameScreen.GameScreenListener;

import javax.swing.*;
import java.awt.*;

public class ToMenuButton extends JButton implements GameScreenListener {

    final private Dimension thisSize = new Dimension(100,30);
    final private ToMenuButtonListener toMenuButtonListener;

    public ToMenuButton(ToMenuButtonListener toMenuButtonListener) {
        this.toMenuButtonListener = toMenuButtonListener;
        setText("to menu");
        addActionListener(e -> this.toMenuButtonListener.gameToMenuButtonClicked());
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
