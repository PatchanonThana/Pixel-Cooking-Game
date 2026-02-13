package window.screen.gameScreen.backgrond;

import window.screen.gameScreen.GameScreenListener;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel implements GameScreenListener {
    public  Background() {
        setBackground(Color.BLACK);
    }

    @Override
    public void gameScreenResized(Dimension size) {
        setBounds(
          0,
          0,
          size.width,
          size.height
        );
    }
}
