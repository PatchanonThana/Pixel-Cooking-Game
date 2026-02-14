package window.screen.menuScreen;

import window.mainWindow.MainWindow;
import window.screen.mainScreen.MainScreen;
import window.screen.menuScreen.background.Background;
import window.screen.menuScreen.menuButton.exitButton.ExitButtonListener;
import window.screen.menuScreen.menuButton.exitButton.MenuExitButton;
import window.screen.menuScreen.menuButton.startButton.MenuStartButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;


public class MenuScreen extends JPanel {

    JLayeredPane menuLayer;
    MainScreen mainScreen;
    MainWindow mainWindow;

    public MenuScreen(MainScreen mainScreen,MainWindow mainWindow) {
        this.mainScreen = mainScreen;
        this.mainWindow = mainWindow;
        menuLayer = new JLayeredPane();
        setLayout(new BorderLayout());
        add(menuLayer, BorderLayout.CENTER);

        Background background = new Background();
        menuLayer.add(background, JLayeredPane.DEFAULT_LAYER);

        MenuStartButton startButton = new MenuStartButton(this.mainScreen);
        menuLayer.add(startButton, JLayeredPane.PALETTE_LAYER);

        List<ExitButtonListener> exitButtonListeners = new ArrayList<>();
        exitButtonListeners.add(mainScreen);
        exitButtonListeners.add(mainWindow);

        MenuExitButton exitButton = new MenuExitButton(exitButtonListeners);
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
