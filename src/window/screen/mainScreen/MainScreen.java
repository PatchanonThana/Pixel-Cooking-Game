package window.screen.mainScreen;

import window.screen.gameScreen.GameScreen;
import window.screen.gameScreen.toMenuButton.ToMenuButton;
import window.screen.gameScreen.toMenuButton.ToMenuButtonListener;
import window.screen.menuScreen.MenuScreen;
import window.screen.menuScreen.menuButton.startButton.MenuStartButtonListener;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JPanel implements MenuStartButtonListener, ToMenuButtonListener {

    CardLayout cardLayout;

    enum Screen {
        MENU,GAME
    }

    public MainScreen() {

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        MenuScreen menuScreen = new MenuScreen(this);
        add(menuScreen, Screen.MENU.name());

        GameScreen gameScreen = new GameScreen(this);
        add(gameScreen, Screen.GAME.name());

        cardLayout.show(this,Screen.MENU.name());

    }

    public void showCard(String name) {
        cardLayout.show(this,name);
    }

    @Override
    public void menuStartButtonClicked() {
        showCard(Screen.GAME.name());
    }

    @Override
    public void gameToMenuButtonClicked() {
        showCard(Screen.MENU.name());
    }



}
