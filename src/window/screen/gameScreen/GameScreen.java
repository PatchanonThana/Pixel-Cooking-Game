package window.screen.gameScreen;

import window.screen.gameScreen.CutBoard.CutBoard;
import window.screen.gameScreen.backgrond.Background;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;
import window.screen.gameScreen.toMenuButton.ToMenuButton;
import window.screen.mainScreen.MainScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameScreen extends JPanel{

    JLayeredPane gameLayer;
    MainScreen mainScreen;
    Pot pot = new Pot();
    CutBoard cutBoard = new CutBoard();

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

        gameLayer.add(pot,JLayeredPane.POPUP_LAYER);
        Ingredient Oil = new Ingredient("/equipment/oil.png", 1270,490,18,38,pot);
        gameLayer.add(Oil , JLayeredPane.DRAG_LAYER);

        Ingredient Pitcher = new Ingredient("/equipment/pitcher.png", 1350,475,29,41,pot);
        gameLayer.add(Pitcher, JLayeredPane.DRAG_LAYER);

        Ingredient SteamerTop = new Ingredient("/equipment/steamertop.png", 1260,680, 50,22,pot);
        gameLayer.add(SteamerTop, JLayeredPane.DRAG_LAYER);

        Ingredient SteamerMid = new Ingredient("/equipment/steamermid.png", 1275,790,46,11,pot);
        gameLayer.add(SteamerMid , JLayeredPane.DRAG_LAYER);

        Ingredient Filling = new Ingredient("/equipment/filling.png", 245,700,10,8,pot);
        gameLayer.add(Filling,JLayeredPane.DRAG_LAYER);

        Ingredient Leaf = new Ingredient("/equipment/leaf.png", 327,683,9,18,pot);
        gameLayer.add(Leaf, JLayeredPane.DRAG_LAYER);

        Ingredient Sesame = new Ingredient("/equipment/sesame.png", 452,670,11,9,pot);
        gameLayer.add(Sesame, JLayeredPane.DRAG_LAYER);

        Ingredient PalmSugar = new Ingredient("/equipment/palmsugar.png", 535,670,11,9,pot);
        gameLayer.add(PalmSugar , JLayeredPane.DRAG_LAYER);

        Dough dough = new Dough(950 , 700,pot,cutBoard);
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
