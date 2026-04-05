package window.screen.gameScreen.ingredient;
import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dough extends Ingredient{
    public Dough(Pot pot, CutBoard cutBoard){
        super("/dessert/dough.png", 0.61 , 0.79,15,9,pot,cutBoard);
        }

    @Override
    protected void OnRightClick(){
        Container parent = getParent();
        Point currentLocation = getLocation();

        double relX = (double) currentLocation.x / parent.getWidth();
        double relY = (double) currentLocation.y / parent.getHeight();

        parent.remove(Dough.this);

        circleDough circledough = new circleDough(relX , relY,pot,cutBoard);
        parent.add(circledough, JLayeredPane.DRAG_LAYER);


        parent.revalidate();
        parent.repaint();
        circledough.gameScreenResized(parent.getSize());

    }
}
