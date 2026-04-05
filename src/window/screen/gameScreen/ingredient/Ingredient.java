package window.screen.gameScreen.ingredient;
import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.pot.Pot;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;


import java.io.IOException;

public class Ingredient extends JComponent implements GameScreenListener {
    private String filename;
    private double relX , relY;
    private int startX, startY;
    private int width, height;
    BufferedImage IngredientImage;
    private boolean isVisible;
    Point imageCorner;
    Point pressedPoint;
    int scale = 5;
    protected Pot pot;
    protected   CutBoard cutBoard;

    public Ingredient(String filename, double relX, double relY, int width, int height, Pot pot) {
        this.filename = filename;
        this.relX = relX;
        this.relY = relY;

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
        setBounds(0,0,this.width,this.height);

        imageCorner = new Point(0,0);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }

    public Ingredient(String filename, double relX,double relY, int width, int height, Pot pot , CutBoard cutBoard) {
        this.filename = filename;
        this.relX = relX;
        this.relY = relY;

        this.isVisible = true;
        setOpaque(false);
        this.width = width*scale;
        this.height = height*scale;
        this.pot = pot;
        this.cutBoard = cutBoard;

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

            if(SwingUtilities.isRightMouseButton(e)){
                OnRightClick();
            }
        }
        public void mouseReleased(MouseEvent e){
            Rectangle ingredientRect = new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());

            System.out.println("ingredient: " + getLocation());
            System.out.println("ingredientRect: " + ingredientRect);
            System.out.println("cutBoard: " + cutBoard);
            System.out.println("boardZone: " + (cutBoard != null ? cutBoard.getBoardZone() : "null"));
            System.out.println("onBoard: " + ((cutBoard != null) && cutBoard.getBoardZone().intersects(ingredientRect)));

            boolean onPot = (pot != null) && pot.getPotZone().intersects(ingredientRect);
            boolean onBoard = (cutBoard != null) && cutBoard.getBoardZone().intersects(ingredientRect);
            if (!onPot && !onBoard){
                setLocation(startX,startY);
            }


        }

    }
    protected void OnRightClick(){}





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
    public void gameScreenResized(Dimension size){
        startX = (int)(size.width * relX);
        startY = (int)(size.height * relY);
        setLocation(startX,startY);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(IngredientImage != null){
            g.drawImage(IngredientImage , 0, 0, getWidth() , getHeight() ,this);
        }

    }

}

