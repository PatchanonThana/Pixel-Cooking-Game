package window.screen.gameScreen.ingredient;

import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.pot.Pot;

import javax.swing.*;
import java.awt.*;

public class Dough extends Ingredient {

    public Dough(Pot pot, CutBoard cutBoard) {
        super("/dessert/dough.png",
                0.61, 0.77,
                0.018, 0.024,
                pot, cutBoard,
                Type.FOOD);
        setPrepState(PrepState.RAW_DOUGH);
    }

    public void resetDough() {
        // คืนค่าให้เป็นแป้งดิบ
        setPrepState(PrepState.RAW_DOUGH);
        this.setVisible(true);
        repaint();
        System.out.println("แป้งถูกรีเซ็ตเรียบร้อย!");
    }

    @Override
    protected void OnRightClick() {
        Container parent = getParent();
        if (parent == null) return;

        Point currentLocation = getLocation();
        Dimension parentSize = parent.getSize();
        double relX = (double) currentLocation.x / parentSize.width;
        double relY = (double) currentLocation.y / parentSize.height;

        this.setVisible(false);
        parent.remove(this);

        circleDough circleDough = new circleDough(relX, relY, pot, cutBoard);

        parent.add(circleDough, JLayeredPane.DRAG_LAYER);
        circleDough.gameScreenResized(parentSize);

        parent.revalidate();
        parent.repaint();
    }
}