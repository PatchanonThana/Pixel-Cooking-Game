package window.screen.gameScreen.cutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Dough;
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
        boolean hasLeaf = ingredients.stream().anyMatch(i->i.getFilename().contains("/equipment/leaf.png"));
        boolean hasSesame = ingredients.stream().anyMatch(i-> i.getFilename().contains("/equipment/sesame.png"));
        boolean hasDoughandFilling = ingredients.stream().anyMatch(i-> i.getFilename().contains("/dessert/ขนมเทียน1.png"));

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

            Ingredient DoughandFiling = new Ingredient("/dessert/ขนมเทียน1.png",0.77,0.79,0.009,0.010,pot,cutBoard);
            ingredients.add(DoughandFiling);
            DoughandFiling.gameScreenResized(gameLayer.getSize());
            gameLayer.add(DoughandFiling,JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();
        }

        if(hasDoughandFilling&&hasLeaf){
            for(Ingredient i : ingredients){
                if(i.getFilename().contains("/dessert/ขนมเทียน1.png")){
                    gameLayer.remove(i);
                } else{
                    i.returnToStart();
                }
            }
            ingredients.clear();


            Ingredient DoughandLeaf = new Ingredient("/dessert/ขนมเทียน2.png",0.77,0.79,0.009,0.010,pot,cutBoard);
            DoughandLeaf.gameScreenResized(gameLayer.getSize());
            gameLayer.add(DoughandLeaf,JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();
        }

        //สูตรข้าวแคบดิบ
        if(hasDough&&hasSesame){
            for(Ingredient i : ingredients){
                i.returnToStart();
            }
            ingredients.clear();


            Ingredient DoughandSesame = new Ingredient("/dessert/ข้าวแคบ1.png",0.77,0.79,0.009,0.010,pot,cutBoard);
            DoughandSesame.gameScreenResized(gameLayer.getSize());
            gameLayer.add(DoughandSesame,JLayeredPane.DRAG_LAYER);
            gameLayer.revalidate();
            gameLayer.repaint();
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
