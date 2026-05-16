package window.screen.gameScreen.trash;

import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.Ingredient;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;

public class trashcan extends JComponent implements GameScreenListener {
    private Image trashImage;
    private int width, height;
    private Runnable onTrashClicked; // ตัวส่งสัญญาณไปล้างค่าอาหาร

    private JLayeredPane gameLayer;
    private Dough mainDough;

    public trashcan() {
        try {
            URL imgUrl = getClass().getResource("Binbutton.png");
            if (imgUrl != null) {
                trashImage = ImageIO.read(imgUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                performTrashLogic();
                if (onTrashClicked != null) {
                    onTrashClicked.run();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setLocation(getX() + 2, getY() + 2);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                setLocation(getX() - 2, getY() - 2);
            }
        });

        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setup(JLayeredPane layer, Dough d) {
        this.gameLayer = layer;
        this.mainDough = d;
    }

    //จัดการการทิ้งขยะ
    private void performTrashLogic() {
        if (gameLayer == null || mainDough == null)
            return;
        // ลบแป้งร่างแปลงในเลเยอร์ DRAG_LAYER
        Component[] comps = gameLayer.getComponentsInLayer(JLayeredPane.DRAG_LAYER);
        for (Component c : comps) {
            if (c instanceof Ingredient ing && ing.getType() == Ingredient.Type.FOOD) {
                if (ing != mainDough) {
                    gameLayer.remove(c);
                }
            }
        }

        // resetแป้งก่อน
        mainDough.resetDough();
        if (mainDough.getParent() == null) {
            gameLayer.add(mainDough, JLayeredPane.DRAG_LAYER);
        }
        mainDough.setVisible(true);
        mainDough.gameScreenResized(gameLayer.getSize());

        // วาดหน้าจอใหม่
        gameLayer.revalidate();
        gameLayer.repaint();
    }

    public void setOnTrashClicked(Runnable action) {
        this.onTrashClicked = action;
    }

    @Override
    public void gameScreenResized(Dimension size) {
        this.height = (int) (size.height * 0.10);
        this.width = (int) (this.height * 1.0);
        int x = (int) (size.width * 0.03);
        int y = (int) (size.height * 0.60);
        setBounds(x, y, width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (trashImage != null) {
            g.drawImage(trashImage, 0, 0, width, height, this);
        }
    }
}