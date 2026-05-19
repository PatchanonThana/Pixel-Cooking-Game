package window.screen.gameScreen.ingredient;
import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.pot.Pot;
import window.screen.gameScreen.customer.Customer;
import window.screen.gameScreen.gametimer.GameTimer;

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
        WATER, OIL, SUGAR,
        FOOD, FILLING, LEAF, SESAME,
        STEAMERMID, STEAMERTOP
    }

    public enum PrepState {
        RAW_DOUGH,
        RING,
        WITH_SESAME,
        WITH_FILLING,
        WRAPPED,
        FRIED,
        STEAMED,
        SYRUP_READY,
        COATED,
        FINISHED
    }

    public enum FoodKind {
        RING, THIAN, KHAEB
    }

    private FoodKind foodKind = null;
    private PrepState prepState = PrepState.RAW_DOUGH;
    protected Type type = Type.FOOD;


    private String filename;
    private double relX , relY; //พิกัดจริง
    private int startX, startY;
    private  int width , height; //พิกัดเริ่ม
    private double relWidth , relHeight;
    BufferedImage IngredientImage;
    Point pressedPoint;
    int scale = 5;
    protected Pot pot;
    protected   CutBoard cutBoard;

    private static Customer customer;

    // เปลี่ยนชื่อฟังก์ชันและใส่ static
    public static void setCustomerTarget(Customer c) {
        Ingredient.customer = c;
    }


    public Ingredient(String filename, double relX, double relY, double relWidth, double relHeight, Pot pot, CutBoard cutBoard, Type type) {
        this.filename = filename;
        this.relX = relX;
        this.relY = relY;
        this.relWidth = relWidth * scale;
        this.relHeight = relHeight * scale;
        this.pot = pot;
        this.cutBoard = cutBoard;
        this.type = type;

        setOpaque(false);
        loadImage(filename);
        setBounds(0, 0, 0, 0);

        addMouseListener(new ClickListener());
        addMouseMotionListener(new DragListener());
    }

    private void loadImage(String path) {
        try {
            java.net.URL imgUrl = getClass().getResource(path);
            if (imgUrl != null) {
                IngredientImage = ImageIO.read(imgUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public FoodKind getFoodKind() {
        return foodKind;
    }

    public void setFoodKind(FoodKind kind) {
        this.foodKind = kind;
    }

    public Type getType() {
        return type;
    }

    public PrepState getPrepState() {
        return prepState;
    }

    public void setPrepState(PrepState state) {
        this.prepState = state;
    }

    public String getFilename() {
        return filename;
    }

    public Image getFoodImage() {
        return IngredientImage;
    }

    public void setImage(String newFilename) {
        this.filename = newFilename;
        loadImage(newFilename);
        repaint();
    }

    public void returnToStart() {
        setLocation(startX, startY);
    }

    private class ClickListener extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if (GameTimer.isTimeUp) return; // 🌟 ถ้าหมดเวลา ให้จบการทำงานทันที!

            pressedPoint = e.getPoint();

            if(SwingUtilities.isRightMouseButton(e)){
                OnRightClick();
            }
        }
        //เช็คการปล่อยวัตถุออกจากเมาส์
        public void mouseReleased(MouseEvent e){
            if (GameTimer.isTimeUp) return;

            Rectangle ingredientRect = new Rectangle(getLocation().x, getLocation().y, getWidth(), getHeight());

            boolean onPot = (pot != null) && pot.getPotZone().intersects(ingredientRect);
            boolean onBoard = (cutBoard != null) && cutBoard.getBoardZone().intersects(ingredientRect);

            boolean onCustomer = (customer != null) && customer.getBounds().intersects(ingredientRect);

            PrepState currentState = getPrepState();
            boolean isCooked = (currentState == PrepState.COATED ||
                    currentState == PrepState.FRIED ||
                    currentState == PrepState.STEAMED);

            if (onCustomer  && isCooked && !customer.isServed) {
                boolean isCorrect = customer.checkOrder(Ingredient.this);
                setVisible(false);

                if (isCorrect) {
                    System.out.println("เสิร์ฟถูกต้อง");
                    // สามารถเพิ่ม Code บวกคะแนนตรงนี้ได้
                } else {
                    System.out.println("เสิร์ฟผิด!");
                    // สามารถเพิ่ม Code ลบคะแนนตรงนี้ได้
                }
                customer.startLeaveAnimation();
            }
            else if (onPot) {
                pot.addIngredient(Ingredient.this);
            }
            else if (onBoard) {
                cutBoard.addIngredient(Ingredient.this);
            }
            else {
                // ถ้าไม่โดนอะไรเลย ให้เด้งกลับที่เดิม
                setLocation(startX, startY);
                if (cutBoard != null) {
                    cutBoard.removeIngredient(Ingredient.this);
                }
            }

        }

    }
    //เอาไว้ให้เรียกแล้วoverrideอีกที
    protected void OnRightClick(){}




    //ลากวัตถุ
    private class DragListener extends MouseMotionAdapter{
        public void mouseDragged(MouseEvent e){
            if (GameTimer.isTimeUp) return;

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
