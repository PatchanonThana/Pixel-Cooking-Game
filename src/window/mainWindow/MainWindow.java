package window.mainWindow;

import app.Main;
import window.screen.mainScreen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.util.random.RandomGenerator;

public class MainWindow extends JFrame {
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JLayeredPane layer = getLayeredPane();
        layer.setLayout(null);

        JButton exit = new JButton("exit");
        exit.addActionListener(e -> System.exit(0));
        exit.setBounds(10,10,60,30);
        exit.setFocusable(false);
        layer.add(exit, JLayeredPane.DEFAULT_LAYER);

        MainScreen mainScreen = new MainScreen();
        add(mainScreen, BorderLayout.CENTER);


        setVisible(true);
    }
}
