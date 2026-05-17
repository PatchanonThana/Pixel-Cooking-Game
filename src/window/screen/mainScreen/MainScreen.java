package window.screen.mainScreen;

import window.mainWindow.MainWindow;
import window.screen.gameScreen.GameScreen;
import window.screen.gameScreen.toMenuButton.ToMenuButtonListener;
import window.screen.menuScreen.MenuScreen;
import window.screen.menuScreen.menuButton.exitButton.ExitButtonListener;
import window.screen.menuScreen.menuButton.startButton.MenuStartButtonListener;
import window.screen.menuScreen.menuButton.kumwanButton.KumwanButtonListener;
import window.soundPlayer.bgmPlayer.BGMPlayer;
import window.soundPlayer.bgmPlayer.InGameBGMPlayer;
import window.soundPlayer.doorSoundPlayer.DoorSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.classfile.ClassFile;
import java.util.Objects;
import java.util.Properties;

public class MainScreen extends JPanel implements
        MenuStartButtonListener,
        ToMenuButtonListener,
        ExitButtonListener,
        KumwanButtonListener {

    CardLayout cardLayout;
    MainWindow mainWindow;
    BGMPlayer bgm;
    InGameBGMPlayer inGameBGMPlayer;
    public String playerName = "Player";
    public int playerScore;
    boolean startButtonClicked = false;
    boolean haveName;
    private final Icon inputIcon;

    private final DoorSoundPlayer doorSoundPlayer;

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
        bgm.start();

        inGameBGMPlayer = new InGameBGMPlayer();

        cardLayout.show(this, Screen.MENU.name());

        ImageIcon rawInputIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/mainScreen/inputDialogIcon/white-stick-controller-in-pixel-art-style-vector.jpg"
        )));
        Image sizedInputIcon = rawInputIcon.getImage().getScaledInstance(32,32, Image.SCALE_SMOOTH);
        inputIcon  = new ImageIcon(sizedInputIcon);

        doorSoundPlayer = new DoorSoundPlayer();

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
            //Load player data from player/data.properties
            Properties prop = new Properties();
            try (FileInputStream input = new FileInputStream("src/player/data.properties")) {
                prop.load(input);
                playerName = prop.getProperty("PlayerName", "NONAME");
                haveName = !playerName.equals("NONAME");
                playerScore = Integer.parseInt(prop.getProperty("PlayerHighestScore","0"));
            }
            catch (IOException err) {
                System.out.println(err.getMessage());
            }

            int changeName;
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
            //tell gameScreen to tell customer to change player name
            gameScreen.changeCustomerPlayerName(playerName);
        }

        bgm.stop();
        inGameBGMPlayer.start();

        doorSoundPlayer.playSound();
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
        bgm.start();
        inGameBGMPlayer.stop();
        doorSoundPlayer.playSound();
        showCard(Screen.MENU.name());
    }

    // --------- Exit ---------
    @Override
    public void exitButtonClicked() {
        bgm.destroy();

        //Save player data at  player/data.properties
        Properties prop = new Properties();
        File file = new File("src/player/data.properties");
        // โหลดข้อมูลเดิม(highestscore) มาเก็บไว้ใน prop ก่อน
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                prop.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        prop.setProperty("PlayerName",this.playerName);

        try (FileOutputStream out = new FileOutputStream(file)) {
            prop.store(out, "Player Data");
        }
        catch (IOException err) {
            System.out.println(err.getMessage());
        }

        System.exit(0);
    }
}