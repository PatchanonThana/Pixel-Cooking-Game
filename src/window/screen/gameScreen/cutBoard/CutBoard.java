package window.screen.gameScreen.cutBoard;

import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.ingredient.Ingredient.PrepState;
import window.screen.gameScreen.ingredient.Ingredient.FoodKind;
import window.screen.gameScreen.ingredient.Ingredient.Type;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.circleDough;
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

    private void findPot() {
        if (pot != null) return;
        JLayeredPane gameLayer = (JLayeredPane) getParent();
        if (gameLayer == null) return;

        for (Component c : gameLayer.getComponents()) {
            if (c instanceof Pot p) {
                pot = p;
                break;
            }
        }
    }

    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
        checkRecipe();
    }

    public void checkRecipe(){
        findPot();

        boolean hasDough = ingredients.stream().anyMatch(i-> i instanceof Dough);
        boolean hasFilling = ingredients.stream().anyMatch(i-> i.getFilename().contains("/equipment/filling.png"));
        boolean hasLeaf = ingredients.stream().anyMatch(i->i.getFilename().contains("/equipment/leaf.png"));
        boolean hasSesame = ingredients.stream().anyMatch(i-> i.getFilename().contains("/equipment/sesame.png"));
        boolean hasDoughandFilling = ingredients.stream().anyMatch(i-> i.getFilename().contains("/dessert/tian1.png"));

        //ทำให้เข้าถึงpot บนgamelayer
        JLayeredPane gameLayer = (JLayeredPane) getParent();
        for(Component c : gameLayer.getComponents()) {
            if(c instanceof Pot p) {
                pot = p;
                break;
            }
        }

        //สูตรขนมเทียนห่อใบตอง
        if(hasDough&&hasFilling){
            for(Ingredient i : ingredients){
                i.returnToStart();
            }
            ingredients.clear();

            Ingredient thian1 = new Ingredient("/dessert/tian1.png", 0.77, 0.79, 0.009, 0.010, pot, this, Type.FOOD);
            thian1.setPrepState(PrepState.WITH_FILLING);
            thian1.setFoodKind(FoodKind.THIAN);

            thian1.gameScreenResized(gameLayer.getSize());
            gameLayer.add(thian1, JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();
            return;
        }

        if(hasDoughandFilling&&hasLeaf){
            for(Ingredient i : ingredients){
                if(i.getFilename().contains("/dessert/tian1.png")){
                    gameLayer.remove(i);
                } else{
                    i.returnToStart();
                }
            }
            ingredients.clear();


            Ingredient thian2 = new Ingredient("/dessert/tian2.png", 0.77, 0.79, 0.009, 0.010, pot, this, Type.FOOD);
            thian2.setPrepState(PrepState.WRAPPED);
            thian2.setFoodKind(FoodKind.THIAN);

            thian2.gameScreenResized(gameLayer.getSize());
            gameLayer.add(thian2, JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();
            return;
        }

        //สูตรข้าวแคบดิบ
        if(hasDough&&hasSesame){
            for(Ingredient i : ingredients){
                i.returnToStart();
            }
            ingredients.clear();


            Ingredient khaeb1 = new Ingredient("/dessert/khaeb1.png", 0.77, 0.79, 0.009, 0.010, pot, this, Type.FOOD);
            khaeb1.setPrepState(PrepState.WITH_SESAME);
            khaeb1.setFoodKind(FoodKind.KHAEB);

            khaeb1.gameScreenResized(gameLayer.getSize());
            gameLayer.add(khaeb1, JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();
            return;
        }


    }

    public void removeIngredient(Ingredient ingredient){
        ingredients.remove(ingredient);
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
