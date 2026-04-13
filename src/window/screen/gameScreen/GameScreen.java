package window.screen.gameScreen;

import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.backgrond.Background;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;
import window.screen.gameScreen.toMenuButton.ToMenuButton;
import window.screen.mainScreen.MainScreen;
import window.screen.gameScreen.customer.Customer;

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

        currentCustomer = new Customer();
        gameLayer.add(currentCustomer, JLayeredPane.PALETTE_LAYER);

        //สร้างวัตถุบนเกมซีนคร้าบ บ บ
        gameLayer.add(cutBoard,JLayeredPane.POPUP_LAYER);
        gameLayer.add(pot,JLayeredPane.POPUP_LAYER);

        Ingredient Oil = new Ingredient("/equipment/oil.png", 0.83,0.57,0.011,0.04,pot);
        gameLayer.add(Oil , JLayeredPane.DRAG_LAYER);

        Ingredient Pitcher = new Ingredient("/equipment/pitcher.png", 0.89,0.54,0.018,0.047,pot);
        gameLayer.add(Pitcher, JLayeredPane.DRAG_LAYER);

        Ingredient SteamerTop = new Ingredient("/equipment/steamertop.png", 0.82,0.78, 0.032,0.025,pot);
        gameLayer.add(SteamerTop, JLayeredPane.DRAG_LAYER);

        Ingredient SteamerMid = new Ingredient("/equipment/steamermid.png", 0.83,0.91,0.029,0.012,pot);
        gameLayer.add(SteamerMid , JLayeredPane.DRAG_LAYER);

        Ingredient Filling = new Ingredient("/equipment/filling.png", 0.15,0.81,0.006,0.009,pot,cutBoard);
        gameLayer.add(Filling,JLayeredPane.DRAG_LAYER);

        Ingredient Leaf = new Ingredient("/equipment/leaf.png", 0.21,0.79,0.005,0.020,pot);
        gameLayer.add(Leaf, JLayeredPane.DRAG_LAYER);

        Ingredient Sesame = new Ingredient("/equipment/sesame.png", 0.30,0.78,0.007,0.010,pot);
        gameLayer.add(Sesame, JLayeredPane.DRAG_LAYER);

        Ingredient PalmSugar = new Ingredient("/equipment/palmsugar.png", 0.35,0.78,0.007,0.010,pot);
        gameLayer.add(PalmSugar , JLayeredPane.DRAG_LAYER);

        Dough dough = new Dough(pot,cutBoard);
        gameLayer.add(dough, JLayeredPane.DRAG_LAYER);



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
}
