package window.screen.mainScreen;

import window.mainWindow.LayerListener;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel{

    CardLayout cardLayout;

    enum Screen {
        MENU,GAME
    }

    public MainScreen() {

        cardLayout = new CardLayout();
        setLayout(cardLayout);

    }
}
