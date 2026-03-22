package window.screen.gameScreen.ingredient;
import window.screen.gameScreen.pot.Pot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

public class Ingredient extends JComponent {
    private String filename;
    private int startX, startY;
    private int width, height;
    BufferedImage IngredientImage;
    private boolean isVisible;
    Point imageCorner;
    Point pressedPoint;
    int scale = 5;
    private Pot pot;

    public Ingredient(String filename, int startX, int startY, int width, int height, Pot pot) {
        this.filename = filename;
        this.startX = startX;
        this.startY = startY;
        this.isVisible = true;
        setOpaque(false);
        this.width = width*scale;
        this.height = height*scale;
        this.pot = pot;

        //รับไฟล์รูป
        try {
            java.net.URL imgUrl = getClass().getResource(filename);
            IngredientImage = ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ตั้งค่าตำแหน่งรุปบนจอหลัก
        setBounds(startX,startY,this.width,this.height);

        imageCorner = new Point(0,0);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }


    private class ClickListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            pressedPoint = e.getPoint();
        }
        public void mouseReleased(MouseEvent e){
            if(!(pot.getPotZone().contains(getLocation()))){
                setLocation(startX,startY);
            }
        }
    }





    private class DragListener extends MouseMotionAdapter{
        public void mouseDragged(MouseEvent e){

            Container parent = getParent();
            Point parentPoint = SwingUtilities.convertPoint(
                    Ingredient.this , e.getPoint() , parent
            );

            int newX = parentPoint.x - pressedPoint.x;
            int newY = parentPoint.y - pressedPoint.y;
            setLocation(newX, newY);
        }


    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(IngredientImage != null){
            g.drawImage(IngredientImage , 0, 0, getWidth() , getHeight() ,this);
        }

    }

}

