package window.screen.mainScreen;

import window.mainWindow.MainWindow;
import window.screen.gameScreen.GameScreen;
import window.screen.gameScreen.toMenuButton.ToMenuButtonListener;
import window.screen.menuScreen.MenuScreen;
import window.screen.menuScreen.menuButton.exitButton.ExitButtonListener;
import window.screen.menuScreen.menuButton.startButton.MenuStartButtonListener;
import window.screen.menuScreen.menuButton.kumwanButton.KumwanButtonListener;
import window.soundPlayer.bgmPlayer.BGMPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class MainScreen extends JPanel implements
        MenuStartButtonListener,
        ToMenuButtonListener,
        ExitButtonListener,
        KumwanButtonListener {

    CardLayout cardLayout;
    MainWindow mainWindow;
    BGMPlayer bgm;
    public String playerName = "Player";
    boolean startButtonClicked = false;
    private Icon inputIcon;

    GameScreen gameScreen;

    enum Screen {
        MENU, GAME
    }

    public MainScreen(MainWindow mainWindow) {
        this.mainWindow = mainWindow;

        cardLayout = new CardLayout();
        setLayout(cardLayout);

        MenuScreen menuScreen = new MenuScreen(this, mainWindow);
        add(menuScreen, Screen.MENU.name());

        gameScreen = new GameScreen(this);
        add(gameScreen, Screen.GAME.name());

        bgm = new BGMPlayer();

        cardLayout.show(this, Screen.MENU.name());

        ImageIcon rawInputIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/mainScreen/inputDialogIcon/white-stick-controller-in-pixel-art-style-vector.jpg"
        )));
        Image sizedInputIcon = rawInputIcon.getImage().getScaledInstance(32,32, Image.SCALE_SMOOTH);
        inputIcon  = new ImageIcon(sizedInputIcon);

    }

    public void showCard(String name) {
        cardLayout.show(this, name);
    }

    //Begging for player name
    public void askForName() {
        playerName = (String) JOptionPane.showInputDialog(
                null
                ,"Please enter you name",
                "Payer Name",
                JOptionPane.QUESTION_MESSAGE,
                inputIcon,
                null,
                ""

        );
        if (playerName == null ||playerName.isBlank()) {
            playerName = "Player";
        }
        showCard(Screen.GAME.name());
    }

    // --------- Start ---------
    @Override
    public void menuStartButtonClicked() {
        if (!startButtonClicked) {
            boolean haveName = false; //just placeholder
            int changeName = JOptionPane.NO_OPTION;
            if (haveName) {
                changeName = JOptionPane.showConfirmDialog(
                        null,
                        "Do you want to change your name?",
                        "Name Change",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null);
                if (changeName == JOptionPane.YES_OPTION) {
                    askForName();
                }
            } else {
                askForName();
            }
            gameScreen.changeCustomerPlayerName(playerName);
        }
        showCard(Screen.GAME.name());
        startButtonClicked = true;

    }

    // --------- Kumwan ---------
    @Override
    public void kumwanButtonClicked() {
        System.out.println("Kumwan Mode");
        showCard(Screen.GAME.name());
    }

    // --------- Back ---------
    @Override
    public void gameToMenuButtonClicked() {
        showCard(Screen.MENU.name());
    }

    // --------- Exit ---------
    @Override
    public void exitButtonClicked() {
        bgm.stop();
        System.exit(0);
    }
}