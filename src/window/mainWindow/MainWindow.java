package window.mainWindow;

import window.screen.mainScreen.MainScreen;
import window.screen.menuScreen.menuButton.exitButton.ExitButtonListener;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame implements ExitButtonListener {
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JLayeredPane layer = getLayeredPane();
        layer.setLayout(null);

        MainScreen mainScreen = new MainScreen(this);
        add(mainScreen, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void exitButtonClicked() {
        this.dispose();
    }
}
