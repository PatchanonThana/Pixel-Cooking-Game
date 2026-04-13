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
    //add
    public enum Type {
        WATER,
        OIL,
        FOOD,
        OTHER
    }protected Type type = Type.OTHER;
    public Type getType() {
        return type;
    }


    private String filename;
    private double relX , relY; //พิกัดจริง
    private int startX, startY;
    private  int width , height; //พิกัดเริ่ม
    private double relWidth , relHeight;
    BufferedImage IngredientImage;
    private boolean isVisible;
    Point imageCorner;
    Point pressedPoint;
    int scale = 5;
    protected Pot pot;
    protected   CutBoard cutBoard;

    public Ingredient(String filename, double relX, double relY, double relWidth, double relHeight, Pot pot) {
        this.filename = filename;
        this.relX = relX;
        this.relY = relY;
        this.isVisible = true;
        setOpaque(false);
        this.relWidth = relWidth * scale;
        this.relHeight = relHeight * scale;
        this.pot = pot;

        //รับไฟล์รูป
        try {
            java.net.URL imgUrl = getClass().getResource(filename);
            IngredientImage = ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //ตั้งค่าตำแหน่งรุปบนจอหลัก
        setBounds(0,0,0,0);

        //เพิ่มระบบเมาส์และตำแหน่งแรกสำหรับคำนวณ
        imageCorner = new Point(0,0);
        ClickListener clickListener = new ClickListener();
        DragListener dragListener = new DragListener();
        this.addMouseListener(clickListener);
        this.addMouseMotionListener(dragListener);
    }
    //add บอกว่ามันคืออะไร --> ตั้งว่า ingredient นี้เป็น WATER,OIL,FOOD
    public Ingredient(String filename, double relX, double relY, double relWidth, double relHeight, Pot pot, Type type) {

        this(filename, relX, relY, relWidth, relHeight, pot);
        this.type = type;
    }

    //constructor สำหรับวัตถุดิบที่ต้องใช้เขียง --> วัตถุดิบนี้ใช้กับเขียงได้
    public Ingredient(String filename, double relX,double relY, double relWidth, double relHeight, Pot pot , CutBoard cutBoard) {
        this(filename , relX , relY , relWidth , relHeight , pot);
        this.cutBoard = cutBoard;
        this.isVisible = true;
        setOpaque(false);

    }

    public String getFilename(){
        return filename;
    }

    public void returnToStart(){
        setLocation(startX,startY);
    }




    private class ClickListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            pressedPoint = e.getPoint();

            if(SwingUtilities.isRightMouseButton(e)){
                OnRightClick();
            }
        }
        //เช็คการปล่อยวัตถุออกจากเมาส์
        public void mouseReleased(MouseEvent e){
            Rectangle ingredientRect = new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());

            boolean onPot = (pot != null) && pot.getPotZone().intersects(ingredientRect);
            boolean onBoard = (cutBoard != null) && cutBoard.getBoardZone().intersects(ingredientRect);
            //add --> ส่งวัตถุดิบนี้เข้าไปในหม้อ ใน Pot เช็ค เป็น WATER → เปลี่ยนเป็นหม้อน้ำ, เป็น OIL → เปลี่ยนเป็นหม้อน้ำมัน
            if (onPot) {
                pot.addIngredient(Ingredient.this);
            }

            //add --> ส่งวัตถุดิบไปเขียง
            if(onBoard){
                cutBoard.addIngredient(Ingredient.this);
            }

            if (!onPot && !onBoard){
                setLocation(startX,startY);
            }


        }

    }
    //เอาไว้ให้เรียกแล้วoverrideอีกที
    protected void OnRightClick(){}




    //ลากวัตถุ
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


    //เพิ่มวัตถุบน gamescreen 2
    @Override
    public void gameScreenResized(Dimension size){
        startX = (int)(size.width * relX);
        startY = (int)(size.height * relY);
        width = (int)(size.width * relWidth);
        height = (int)(size.height * relHeight);
        setBounds(startX , startY , width,height);
    }

    //วาดวัตถุเริ่มต้น 1
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(IngredientImage != null){
            g.drawImage(IngredientImage , 0, 0, getWidth() , getHeight() ,this);
        }

    }

}

