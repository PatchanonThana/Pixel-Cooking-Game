package window.mainWindow;

import app.Main;
import window.screen.mainScreen.MainScreen;
import window.soundPlayer.bgmPlayer.BGMPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MainWindow extends JFrame {
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JLayeredPane layer = getLayeredPane();
        layer.setLayout(null);

        MainScreen mainScreen = new MainScreen();
        add(mainScreen, BorderLayout.CENTER);
        setVisible(true);
    }
}
