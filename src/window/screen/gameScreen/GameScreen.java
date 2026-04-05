package window.screen.gameScreen;

import window.screen.gameScreen.cutBoard.CutBoard;
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

        gameLayer.add(cutBoard,JLayeredPane.POPUP_LAYER);
        gameLayer.add(pot,JLayeredPane.POPUP_LAYER);

        Ingredient Oil = new Ingredient("/equipment/oil.png", 0.82,0.56,18,38,pot);
        gameLayer.add(Oil , JLayeredPane.DRAG_LAYER);

        Ingredient Pitcher = new Ingredient("/equipment/pitcher.png", 0.87,0.54,29,41,pot);
        gameLayer.add(Pitcher, JLayeredPane.DRAG_LAYER);

        Ingredient SteamerTop = new Ingredient("/equipment/steamertop.png", 0.82,0.78, 50,22,pot);
        gameLayer.add(SteamerTop, JLayeredPane.DRAG_LAYER);

        Ingredient SteamerMid = new Ingredient("/equipment/steamermid.png", 0.83,0.91,46,11,pot);
        gameLayer.add(SteamerMid , JLayeredPane.DRAG_LAYER);

        Ingredient Filling = new Ingredient("/equipment/filling.png", 0.15,0.81,10,8,pot);
        gameLayer.add(Filling,JLayeredPane.DRAG_LAYER);

        Ingredient Leaf = new Ingredient("/equipment/leaf.png", 0.21,0.79,9,18,pot);
        gameLayer.add(Leaf, JLayeredPane.DRAG_LAYER);

        Ingredient Sesame = new Ingredient("/equipment/sesame.png", 0.30,0.77,11,9,pot);
        gameLayer.add(Sesame, JLayeredPane.DRAG_LAYER);

        Ingredient PalmSugar = new Ingredient("/equipment/palmsugar.png", 0.35,0.77,11,9,pot);
        gameLayer.add(PalmSugar , JLayeredPane.DRAG_LAYER);

        Dough dough = new Dough(pot,cutBoard);
        gameLayer.add(dough, JLayeredPane.DRAG_LAYER);



        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                gameLayer.setBounds(0, 0, getWidth(), getHeight());
                System.out.println("width: " + getWidth());
                System.out.println("height: " + getHeight());
                for (Component c : gameLayer.getComponents()) {
                    if (c instanceof GameScreenListener r) {
                        r.gameScreenResized(gameLayer.getSize());
                    }
                }
            }
        });




    }
}
