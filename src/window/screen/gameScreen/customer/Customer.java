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
import window.soundPlayer.DoorBellSoundPlayer.DoorBellSoundPlayer;
import window.soundPlayer.correctSoundPlayer.CorrectSoundPlayer;
import window.soundPlayer.incorrectSoundPlayer.IncorrectSoundPlayer;
import window.soundPlayer.walingSoundPlayer.WalkingSoundPlayer;

public class Customer extends JComponent implements GameScreenListener, MenuStartButtonListener {

    private Image customerImage;
    private Image chatImage;
    private String currentOrder;
    private String displayMessage;
    private Timer messageTimer;
    public boolean isServed = false;

    private Timer orderTimer;
    private double orderTimeLimit = 20.0;
    private double timeLeft = 20.0;

    private final String[] menuItems = {"ขนมวง", "ขนมเทียน", "ขนมแคบ"};

    private int charW = 1250;
    private int charH = 1050;

    private Timer moveTimer;
    private Runnable onExitCallback;
    String playerName;

    final private CorrectSoundPlayer correctSoundPlayer;
    final private IncorrectSoundPlayer incorrectSoundPlayer;
    final private WalkingSoundPlayer walkingSoundPlayer;
    private boolean hasPlayedEnterSound = false;
    private final DoorBellSoundPlayer doorBellSoundPlayer;

    public Customer() {
        Random random = new Random();
        int menuIndex = random.nextInt(menuItems.length);
        this.currentOrder = menuItems[menuIndex];
        int spriteNumber = random.nextInt(3) + 1;
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

        correctSoundPlayer = new CorrectSoundPlayer();
        incorrectSoundPlayer = new IncorrectSoundPlayer();
        walkingSoundPlayer = new WalkingSoundPlayer();
        doorBellSoundPlayer = new DoorBellSoundPlayer();

        setOpaque(false);

        startOrderTimer();
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

    public void startOrderTimer() {
        timeLeft = orderTimeLimit;
        orderTimer = new Timer(20, e -> {
            timeLeft -= 0.02 ;

            if (timeLeft <= 0) {
                // เมื่อเวลาหมด
                // หักคะแนนหมดเวลา
                if (Point.getInstance() != null) {
                    Point.getInstance().timeOutPunish();
                }
                //ลบคะแนนทันทีไม่รอให้ลูกค้าออกก่อน
                if (getParent() != null) {
                    getParent().repaint();
                }
                this.isServed = true;
                this.displayMessage = "ทำไมร้านนี้ทำช้าจัง";
                orderTimer.stop();
                startLeaveAnimation();
            }

            repaint();
        });

        //orderTimer.setRepeats(false); // สั่งให้จับเวลาแค่รอบเดียวต่อคน
        orderTimer.start();
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
                Point.getInstance().processService(currentOrder, currentOrder, (int)timeLeft);
                correctSoundPlayer.playSound();
                this.displayMessage = "อาหารอร่อยมากเลย";
            } else {
                Point.getInstance().processService(currentOrder, "WRONG_FOOD", 0);
                incorrectSoundPlayer.playSound();
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
        if (orderTimer != null && orderTimer.isRunning()) {
            orderTimer.stop();
        }

        this.isServed = true;
        // หน่วงเวลา2.0วินาที และลบข้อความ
        Timer delayTimer = new Timer(2000, e -> {
            this.chatImage = null;
            this.displayMessage = "";
            repaint();

            // ลูกค้าเดินไปทางซ้ายออกจากร้าน
            moveTimer = new Timer(10, e2 -> {
                setLocation(getX() - 5, getY());

                if (getX() + getWidth() < 0) {
                    moveTimer.stop();
                    walkingSoundPlayer.stop();

                    if (onExitCallback != null) {
                        onExitCallback.run();
                    }
                }
            });
            moveTimer.start();
            walkingSoundPlayer.playSound();
        });

        delayTimer.setRepeats(false);
        delayTimer.start();
    }

    public void playEnterSound() {
        if (!hasPlayedEnterSound) {
            doorBellSoundPlayer.playSound();
        }
        hasPlayedEnterSound = true;
    }


    @Override
    public void gameScreenResized(Dimension size) {
        //ขนาดตัวละคร
        charH = (int) (size.height * 0.7);
        charW = (int) (charH * 1);

        // ตำแหน่งตัวละคร
        int responsiveX = (int) (size.width * 0.39);
        int responsiveY = (int) (size.height * 0.766) - charH;

        //กรอบใสที่ใส่ตัวละครและกล่องข้อความ
        setBounds(responsiveX, responsiveY, (int) (charW * 3), (int) (charH * 0.915));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (customerImage != null) {
            //เลื่อนตัวละครลงมาวาดที่Y=50เพื่อเผื่อพื้นที่ด้านบนให้กล่องข้อความ
            g.drawImage(customerImage, -200, 120, charW, charH, this);
        }

        if (timeLeft > 0 && !isServed) {
            //int maxBarWidth = (int) (charW * 0.8);
            //int barHeight = (int) (charH * 0.05);
            int maxBarWidth = (int) (charW * 0.4);
            int barHeight = (int) (charH * 0.03);

            int currentBarWidth = (int) (((double) timeLeft / orderTimeLimit) * maxBarWidth);

            int barX = (charW - maxBarWidth) / 2 -(int)(charH * 0.27);
            int barY = (int)(charH * 0.25) - barHeight;

            //วาดพื้นหลังหลอด(สีดำ)
            g.setColor(Color.BLACK);
            g.fillRect(barX, barY, maxBarWidth, barHeight);

            if (timeLeft <= 5) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.GREEN);
            }

            //วาดหลอดเวลาที่ลงเรื่อยๆ ทับลงไป
            g.fillRect(barX, barY, currentBarWidth, barHeight);
            //วาดกรอบสีขาวรอบ
            g.setColor(Color.WHITE);
            g.drawRect(barX, barY, maxBarWidth, barHeight);
        }

        // วาดกล่องข้อความ
        if (chatImage != null) {
            g.setFont(new Font("Leelawadee UI", Font.BOLD, 24));

            String fullText = displayMessage;
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int textWidth = metrics.stringWidth(fullText);

            int chatWidth = Math.max(400, textWidth + 200);
            int chatHeight = 280;

            int chatX = (int) (charW * 0.43);
            int chatY = (int) (charH * 0.2);

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