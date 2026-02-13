package window.mainWindow;

import javax.swing.*;
import java.util.random.RandomGenerator;

public class MainWindow extends JFrame {
    public MainWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JLayeredPane layer = getLayeredPane();
        layer.setLayout(null);

        JButton exit = new JButton("exit");
        exit.addActionListener(e -> System.exit(0));
        exit.setBounds(10,10,60,30);
        exit.setFocusable(false);
        layer.add(exit, JLayeredPane.DEFAULT_LAYER);


        setVisible(true);
    }
}
