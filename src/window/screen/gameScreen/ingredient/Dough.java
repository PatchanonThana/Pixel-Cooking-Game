package window.screen.gameScreen.ingredient;

import java.awt.*;
import javax.swing.*;
import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.pot.Pot;

public class Dough extends Ingredient {
    // ตำแหน่งเริ่มต้น
    private final double startX = 0.61;
    private final double startY = 0.77;

    public Dough(Pot pot, CutBoard cutBoard) {
        super("/dessert/dough.png",
                0.61, 0.77,
                0.018, 0.024,
                pot, cutBoard,
                Type.FOOD);
        setPrepState(PrepState.RAW_DOUGH);
    }

    @Override
    protected void OnRightClick() {
        Container parent = getParent();
        if (parent == null) return;

        Point currentLocation = getLocation();
        Dimension parentSize = parent.getSize();

        double relX = (double) currentLocation.x / parentSize.width;
        double relY = (double) currentLocation.y / parentSize.height;

        // สร้าง circleDough ตรงตำแหน่งปัจจุบัน
        circleDough circleDough = new circleDough(relX, relY, pot, cutBoard);

        parent.add(circleDough, JLayeredPane.DRAG_LAYER);
        circleDough.gameScreenResized(parentSize);

        // ===== ย้าย Dough กลับจุดเริ่มต้น =====
        int resetX = (int)(startX * parentSize.width);
        int resetY = (int)(startY * parentSize.height);

        setBounds(resetX, resetY, getWidth(), getHeight());

        parent.revalidate();
        parent.repaint();
    }

    public void resetDough() {
        // คืนค่าให้เป็นแป้งดิบ
        setPrepState(PrepState.RAW_DOUGH);
        this.setVisible(true);
        repaint();
        System.out.println("แป้งถูกรีเซ็ตเรียบร้อย!");
    }
}