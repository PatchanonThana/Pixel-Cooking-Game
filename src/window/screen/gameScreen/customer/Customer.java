package window.screen.gameScreen.customer;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.GameScreenListener;
import window.screen.menuScreen.menuButton.startButton.MenuStartButtonListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import window.screen.gameScreen.point.Point;

public class Customer extends JComponent implements GameScreenListener, MenuStartButtonListener {

    private Image customerImage;
    private Image chatImage;
    private String currentOrder;
    private String displayMessage;
    private Timer messageTimer;

    private final String[] menuItems = {"ขนมวง", "ขนมเทียน", "ขนมแคบ"};

    private int charW = 450;
    private int charH = 550;

    private Timer moveTimer;
    private Runnable onExitCallback;
    String playerName;

    public Customer() {
        Random random = new Random();
        int menuIndex = random.nextInt(menuItems.length);
        this.currentOrder = menuItems[menuIndex];
        int spriteNumber = random.nextInt(4) + 1;
        this.displayMessage = "เชฟผมขอสั่งเมนู(" + currentOrder + ")";
        setOpaque(false);

        String filename = "/window/screen/gameScreen/customer/pixelcustomer/sprite" + spriteNumber + ".png";
        String chatFilename = "/window/screen/gameScreen/customer/pixelcustomer/chat.png";

        try {
            URL imgUrl = getClass().getResource(filename);
            if (imgUrl != null) {
                customerImage = ImageIO.read(imgUrl);
            } else {
                System.out.println("Warning: หาไฟล์ภาพไม่พบที่ " + filename);
            }

            URL chatImgUrl = getClass().getResource(chatFilename);
            if (chatImgUrl != null) {
                chatImage = ImageIO.read(chatImgUrl);
            } else {
                System.out.println("Warning: หาไฟล์ภาพไม่พบที่ " + chatFilename);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        setOpaque(false);
    }

    public void setNewPlayerName(String playerName) {
        this.playerName = playerName;
        this.displayMessage = "เชฟ " + playerName + " ขอสั่งเมนู " + currentOrder + " หน่อย";
    }

    @Override
    public void menuStartButtonClicked() {
        this.displayMessage = "เชฟ " + playerName + " ขอสั่งเมนู " + currentOrder + " หน่อย";
    }


    public String getCurrentOrder() {
        return currentOrder;
    }

    public boolean checkOrder(Ingredient food) {
        //ดึงข้อมูลชนิดอาหารและสถานะจากที่ลากมา
        Ingredient.FoodKind kind = food.getFoodKind();
        Ingredient.PrepState prep = food.getPrepState();

        boolean isCorrect = false;

        //เช็คว่าตรงกับเมนูที่สั่ง และ ทำสุกตามขั้นตอนหรือยัง
        if (currentOrder.equals("ขนมวง") && kind == Ingredient.FoodKind.RING && prep == Ingredient.PrepState.COATED) {
            isCorrect = true;
        } else if (currentOrder.equals("ขนมแคบ") && kind == Ingredient.FoodKind.KHAEB && prep == Ingredient.PrepState.FRIED) {
            isCorrect = true;
        } else if (currentOrder.equals("ขนมเทียน") && kind == Ingredient.FoodKind.THIAN && prep == Ingredient.PrepState.STEAMED) {
            isCorrect = true;
        }

        //เช็คว่าส่งอาหารถูกมั้ยและคำนวณคะแนน
        if (Point.getInstance() != null) {
            if (isCorrect) {
                Point.getInstance().processService(currentOrder, currentOrder);
                this.displayMessage = "อาหารอร่อยมากเลย";
            } else {
                Point.getInstance().processService(currentOrder, "WRONG_FOOD");
                this.displayMessage = "ร้านนี้ไม่ดีเลยทำอาหารก็ผิด";
            }
        }
        //อัปเดตหน้าจอ
        if (getParent() != null) {
            getParent().repaint();
        }

        repaint();
        return isCorrect;
    }

    public void setOnExitCallback(Runnable callback) {
        this.onExitCallback = callback;
    }

    public void startLeaveAnimation() {
        // หน่วงเวลา2.0วินาที และลบข้อความ
        Timer delayTimer = new Timer(2000, e -> {
            this.chatImage = null;
            this.displayMessage = "";
            repaint();

            // ลูกค้าเดินไปทางซ้ายออกจากร้าน
            moveTimer = new Timer(10, e2 -> {
                setLocation(getX() - 5, getY()); // ขยับทีละ 5 พิกเซล

                // เช็คว่าเดินทะลุขอบจอซ้ายไปหรือยัง
                if (getX() + getWidth() < 0) {
                    moveTimer.stop();

                    if (onExitCallback != null) {
                        onExitCallback.run();
                    }
                }
            });
            moveTimer.start();
        });

        delayTimer.setRepeats(false);
        delayTimer.start();
    }


    @Override
    public void gameScreenResized(Dimension size) {
        //ขนาดตัวละคร
        charH = (int) (size.height * 0.50);
        charW = (int) (charH * 0.81);

        // ตำแหน่งตัวละคร
        int responsiveX = (int) (size.width * 0.39);
        int responsiveY = (int) (size.height * 0.766) - charH;

        //กรอบใสที่ใส่ตัวละครและกล่องข้อความ
        setBounds(responsiveX, responsiveY, (int) (charW * 3), (int) (charH * 0.88));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (customerImage != null) {
            //เลื่อนตัวละครลงมาวาดที่Y=50เพื่อเผื่อพื้นที่ด้านบนให้กล่องข้อความ
            g.drawImage(customerImage, 0, 50, charW, charH, this);
        }

        // วาดกล่องข้อความ
        if (chatImage != null) {
            g.setFont(new Font("Leelawadee UI", Font.BOLD, 24));

            String fullText = displayMessage;
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int textWidth = metrics.stringWidth(fullText);

            int chatWidth = Math.max(400, textWidth + 200);
            int chatHeight = 280;

            int chatX = charW - 80;
            int chatY = 0;

            g.drawImage(chatImage, chatX, chatY, chatWidth, chatHeight, this);


            int textX = chatX + ((chatWidth - textWidth) / 2) + 15;
            int textY = chatY + ((chatHeight - metrics.getHeight()) / 2) + metrics.getAscent() - 20;

            if (fullText.contains(currentOrder) && fullText.contains("ขอสั่งเมนู")) {

                // หั่นข้อความเป็น 3 ท่อน
                int orderIndex = fullText.indexOf(currentOrder);
                String part1 = fullText.substring(0, orderIndex);
                String part2 = currentOrder;
                String part3 = fullText.substring(orderIndex + currentOrder.length());

                g.setColor(Color.BLACK);
                g.drawString(part1, textX, textY);

                int part1Width = metrics.stringWidth(part1);
                g.setColor(Color.RED);
                g.drawString(part2, textX + part1Width, textY);

                int part2Width = metrics.stringWidth(part2);
                g.setColor(Color.BLACK);
                g.drawString(part3, textX + part1Width + part2Width, textY);

            } else {
                g.setColor(Color.BLACK);
                g.drawString(fullText, textX, textY);
            }
        }
    }
}