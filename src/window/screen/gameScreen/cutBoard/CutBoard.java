package window.screen.gameScreen.cutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CutBoard extends JComponent implements GameScreenListener {
    Rectangle boardZone;
    Pot pot;
    CutBoard cutBoard;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public CutBoard(){
        boardZone = new Rectangle();
    }

    public Rectangle getBoardZone(){
        return boardZone;
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
        checkRecipe();
    }

    public void checkRecipe(){
        boolean hasDough = ingredients.stream().anyMatch(i-> i instanceof Dough);
        boolean hasFilling = ingredients.stream().anyMatch(i-> i.getFilename().contains("/equipment/filling.png"));

        if(hasDough&&hasFilling){
            for(Ingredient i : ingredients){
                i.returnToStrat();
            }
            ingredients.clear();

            JLayeredPane gameLayer = (JLayeredPane) getParent();
            Ingredient DoughandFiling = new Ingredient("/dessert/ขนมเทียน1.png",0.77,0.79,0.009,0.010,pot,cutBoard);
            DoughandFiling.gameScreenResized(gameLayer.getSize());
            gameLayer.add(DoughandFiling,JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();

        }
    }
    @Override
    public void gameScreenResized(Dimension size){
        int x = (int)(size.width * 0.77);
        int y = (int)(size.height * 0.79);
        int w = (int)(size.width * 0.04);
        int h = (int)(size.height * 0.11);
        boardZone = new Rectangle(x,y,w,h);
        setBounds(x,y,w,h);
    }
}
