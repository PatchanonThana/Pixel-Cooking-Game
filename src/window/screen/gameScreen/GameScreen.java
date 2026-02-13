package window.screen.gameScreen;

import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel{

    JLayeredPane gameLayer;

    public GameScreen() {
        gameLayer = new JLayeredPane();
        gameLayer.setLayout(null);
        setLayout(new BorderLayout());
        add(gameLayer, BorderLayout.CENTER);
    }
}
