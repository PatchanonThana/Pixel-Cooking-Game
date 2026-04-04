package window.screen.gameScreen.ingredient;
import window.screen.gameScreen.CutBoard.CutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dough extends Ingredient{
    public Dough(int startX , int startY , Pot pot, CutBoard cutBoard){
        super("/dessert/dough.png", 950 , 700,15,9,pot,cutBoard);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if(SwingUtilities.isRightMouseButton(e)){
                    Container parent = getParent();
                    Point currentLocation = getLocation();

                    parent.remove(Dough.this);

                    circleDough circledough = new circleDough(currentLocation.x,currentLocation.y,pot,cutBoard);
                    parent.add(circledough, JLayeredPane.DRAG_LAYER);
                    parent.revalidate();
                    parent.repaint();
                }
            }
        });
    }
}
