package window.screen.gameScreen;

import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.backgrond.Background;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;
import window.screen.gameScreen.toMenuButton.ToMenuButton;
import window.screen.mainScreen.MainScreen;
import window.screen.gameScreen.customer.Customer;
import window.screen.gameScreen.point.Point;
import window.screen.gameScreen.trash.trashcan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameScreen extends JPanel{

    JLayeredPane gameLayer;
    MainScreen mainScreen;
    Pot pot = new Pot();
    CutBoard cutBoard = new CutBoard();
    Customer currentCustomer;

    Dough dough;
    trashcan trashBtn;

    String playerName;


    public GameScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
        gameLayer = new JLayeredPane();
        gameLayer.setLayout(null);
        setLayout(new BorderLayout());
        add(gameLayer, BorderLayout.CENTER);

        Background background = new Background();
        gameLayer.add(background, JLayeredPane.DEFAULT_LAYER);

        ToMenuButton toMenuButton = new ToMenuButton(mainScreen);
        gameLayer.add(toMenuButton, JLayeredPane.PALETTE_LAYER);

        //currentCustomer = new Customer();
        //gameLayer.add(currentCustomer, JLayeredPane.PALETTE_LAYER);
        spawnNewCustomer();

        gameLayer.add(cutBoard,JLayeredPane.POPUP_LAYER);
        gameLayer.add(pot,JLayeredPane.POPUP_LAYER);

        new Point();

        Ingredient oil = new Ingredient(
                "/equipment/oil.png",
                0.83, 0.57, 0.011, 0.04,
                pot, null,
                Ingredient.Type.OIL
        );
        gameLayer.add(oil, JLayeredPane.DRAG_LAYER);

        // น้ำ
        Ingredient water = new Ingredient(
                "/equipment/pitcher.png",
                0.89, 0.54, 0.018, 0.047,
                pot, null,
                Ingredient.Type.WATER
        );
        gameLayer.add(water, JLayeredPane.DRAG_LAYER);

        // ฝาซึ้ง
        Ingredient steamerTop = new Ingredient(
                "/equipment/steamertop.png",
                0.82, 0.78, 0.032, 0.025,
                pot, null,
                Ingredient.Type.STEAMERTOP
        );
        gameLayer.add(steamerTop, JLayeredPane.DRAG_LAYER);

        // ซึ้ง
        Ingredient steamerMid = new Ingredient(
                "/equipment/steamermid.png",
                0.83, 0.91, 0.029, 0.012,
                pot, null,
                Ingredient.Type.STEAMERMID
        );
        gameLayer.add(steamerMid, JLayeredPane.DRAG_LAYER);

        // ไส้ขนม
        Ingredient filling = new Ingredient(
                "/equipment/filling.png",
                0.15, 0.81, 0.006, 0.009,
                pot, cutBoard,
                Ingredient.Type.FILLING
        );
        gameLayer.add(filling, JLayeredPane.DRAG_LAYER);

        // ใบตอง
        Ingredient leaf = new Ingredient(
                "/equipment/leaf.png",
                0.21, 0.79, 0.005, 0.020,
                pot, cutBoard,
                Ingredient.Type.LEAF
        );
        gameLayer.add(leaf, JLayeredPane.DRAG_LAYER);

        // งา
        Ingredient sesame = new Ingredient(
                "/equipment/sesame.png",
                0.30, 0.78, 0.007, 0.010,
                pot, cutBoard,
                Ingredient.Type.SESAME
        );
        gameLayer.add(sesame, JLayeredPane.DRAG_LAYER);

        // น้ำตาลปี๊บ
        Ingredient sugar = new Ingredient(
                "/equipment/palmsugar.png",
                0.35, 0.78, 0.007, 0.010,
                pot, null,
                Ingredient.Type.SUGAR
        );
        gameLayer.add(sugar, JLayeredPane.DRAG_LAYER);

        this.dough = new Dough(pot, cutBoard);
        gameLayer.add(this.dough, JLayeredPane.DRAG_LAYER);

        this.trashBtn = new trashcan();
        this.trashBtn.setup(gameLayer, this.dough);
        this.trashBtn.setOnTrashClicked(() -> {
        });

        gameLayer.add(this.trashBtn, JLayeredPane.POPUP_LAYER);
        this.trashBtn.gameScreenResized(gameLayer.getSize());

        Ingredient.setCustomerTarget(currentCustomer);


        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameLayer.setBounds(0, 0, getWidth(), getHeight());
                for (Component c : gameLayer.getComponents()) {
                    if (c instanceof GameScreenListener r) {
                        r.gameScreenResized(gameLayer.getSize());
                    }
                }
            }
        });
    }

    //เรียกลูกค้าใหม่หลังจากลูกค้ารับออเดอร์
    public void spawnNewCustomer() {
        if (currentCustomer != null) {
            gameLayer.remove(currentCustomer);
        }

        currentCustomer = new Customer();

        if (this.playerName != null) {
            currentCustomer.setNewPlayerName(this.playerName);
        }

        currentCustomer.setOnExitCallback(() -> { spawnNewCustomer(); });
        gameLayer.add(currentCustomer, JLayeredPane.PALETTE_LAYER);
        currentCustomer.gameScreenResized(gameLayer.getSize());

        Ingredient.setCustomerTarget(currentCustomer);

        gameLayer.revalidate();
        gameLayer.repaint();
    }

    //เขียนคะแนน
    @Override
    protected void paintChildren(Graphics g) {
        super.paintChildren(g);
        Graphics2D g2 = (Graphics2D) g;
        if (Point.getInstance() != null) {
            Point.getInstance().draw(g2, getWidth());
        }
    }

    //tell customer to change plaeyr name
    public void changeCustomerPlayerName(String playerName) {
        currentCustomer.setNewPlayerName(playerName);
        this.playerName =  playerName;
    }

}