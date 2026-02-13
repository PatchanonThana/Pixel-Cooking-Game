package window.screen.gameScreen;

import window.screen.gameScreen.toMenuButton.ToMenuButton;
import window.screen.mainScreen.MainScreen;
import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameScreen extends JPanel{

    JLayeredPane gameLayer;
    MainScreen mainScreen;

    public GameScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        gameLayer = new JLayeredPane();
        gameLayer.setLayout(null);
        setLayout(new BorderLayout());
        add(gameLayer, BorderLayout.CENTER);

        ToMenuButton toMenuButton = new ToMenuButton(mainScreen);
        gameLayer.add(toMenuButton, JLayeredPane.PALETTE_LAYER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Component c : gameLayer.getComponents()) {
                    if (c instanceof GameScreenListener r) {
                        r.gameScreenResized(gameLayer.getSize());
                    }
                }
            }
        });
    }
}
