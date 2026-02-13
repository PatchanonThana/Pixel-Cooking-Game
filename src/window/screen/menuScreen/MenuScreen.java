package window.screen.menuScreen;

import app.Main;
import window.screen.mainScreen.MainScreen;
import window.screen.menuScreen.background.Background;
import window.screen.menuScreen.menuButton.exitButton.MenuExitButton;
import window.screen.menuScreen.menuButton.startButton.MenuStartButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class MenuScreen extends JPanel {

    JLayeredPane menuLayer;
    MainScreen mainScreen;

    public MenuScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        menuLayer = new JLayeredPane();
        setLayout(new BorderLayout());
        add(menuLayer, BorderLayout.CENTER);

        Background background = new Background();
        menuLayer.add(background, JLayeredPane.DEFAULT_LAYER);

        MenuStartButton startButton = new MenuStartButton(this.mainScreen);
        menuLayer.add(startButton, JLayeredPane.PALETTE_LAYER);

        MenuExitButton exitButton = new MenuExitButton();
        menuLayer.add(exitButton, JLayeredPane.PALETTE_LAYER);

        menuLayer.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                for (Component c : menuLayer.getComponents()) {
                    if (c instanceof MenuListener r) {
                        r.menuResized(menuLayer.getSize());
                    }
                }
            }
        });

    }

}
