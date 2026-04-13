package window.screen.gameScreen.pot;

import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Ingredient;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Pot extends JComponent implements GameScreenListener {
    private PotState state = PotState.EMPTY;
    private Rectangle potZone;
    private Image emptyImage;
    private Image waterImage;
    private Image oilImage;
    private Image currentImage;

    public enum PotState {
        EMPTY,
        WATER,
        OIL
    }

    public Pot() {
        potZone = new Rectangle();
        emptyImage = loadImage("/equipment/emptypot.png");
        waterImage = loadImage("/equipment/waterpot.png");
        oilImage = loadImage("/equipment/oilpot.png");
        currentImage = emptyImage;
    }

    private Image loadImage(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Image not found: " + path);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    public Rectangle getPotZone(){
        return potZone;
    }

    public void addIngredient(Ingredient ing) {

        System.out.println("HIT addIngredient");

        if (!getBounds().intersects(ing.getBounds())) {
            System.out.println("NOT INTERSECT");
            return;
        }

        System.out.println("INTERSECT OK");

        if (ing.getType() == Ingredient.Type.WATER) {
            System.out.println("WATER");
            currentImage = waterImage;
        }
        else if (ing.getType() == Ingredient.Type.OIL) {
            System.out.println("OIL");
            currentImage = oilImage;
        }
        else{
            System.out.println("OTHER");
        }
        repaint();
    }

    @Override
    public void gameScreenResized(Dimension size){

        int w = (int)(size.width * 0.15);
        int h = (int)(size.height * 0.15);

        int x = (size.width - w) / 2;
        int y = (int)(size.height * 0.75);

        potZone = new Rectangle(x, y, w, h);
        setBounds(x, y, w, h);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentImage != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(currentImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}