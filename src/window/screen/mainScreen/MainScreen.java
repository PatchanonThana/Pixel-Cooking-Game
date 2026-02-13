package window.screen.mainScreen;

import window.mainWindow.LayerListener;
import window.screen.menuScreen.MenuScreen;

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

        MenuScreen menuScreen = new MenuScreen();
        add(menuScreen, Screen.MENU.name());

        cardLayout.show(this,Screen.MENU.name());

    }

    public void showCard(String name) {
        cardLayout.show(this,name);
    }

}
