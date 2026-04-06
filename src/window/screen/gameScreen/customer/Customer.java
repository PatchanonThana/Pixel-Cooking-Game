package window.screen.gameScreen.customer;

import window.screen.gameScreen.GameScreenListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Customer extends JComponent implements GameScreenListener {

    private Image customerImage;
    private Image chatImage;
    private String currentOrder;

    private final String[] menuItems = {"ขนมวง", "ขนมเทียน", "ขนมแคบ"};

    private int charW = 450;
    private int charH = 550;

    public Customer() {
        Random random = new Random();
        int menuIndex = random.nextInt(menuItems.length);
        this.currentOrder = menuItems[menuIndex];
        int spriteNumber = random.nextInt(4) + 1;

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

    public String getCurrentOrder() {
        return currentOrder;
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
        setBounds(responsiveX, responsiveY, (int) (charW * 1.8), (int) (charH * 0.88));
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
            int chatWidth = 400;
            int chatHeight = 280;

            int chatX = charW - 80;
            int chatY = 0;

            g.drawImage(chatImage, chatX, chatY, chatWidth, chatHeight, this);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Tahoma", Font.BOLD, 24));

            String fullText = "ขอสั่งเมนู(" + currentOrder + ")";
            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int textWidth = metrics.stringWidth(fullText);

            int textX = chatX + ((chatWidth - textWidth) / 2) + 15;
            int textY = chatY + ((chatHeight - metrics.getHeight()) / 2) + metrics.getAscent() - 20;

            g.drawString(fullText, textX, textY);
        }
    }
}