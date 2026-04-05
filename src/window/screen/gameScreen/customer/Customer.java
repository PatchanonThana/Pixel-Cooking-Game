package window.screen.gameScreen.customer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Customer extends JComponent {

    private Image customerImage;
    private Image chatImage;
    private String currentOrder;

    private final String[] menuItems = {"ขนมวง", "ขนมเทียน", "ขนมแคบ"};

    public Customer(int startX, int startY, int width, int height) {
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

        setBounds(startX, startY, width, height);
        setOpaque(false);
    }

    // ฟังก์ชันสำหรับส่งค่าเมนูที่ลูกค้าสั่ง
    public String getCurrentOrder() {
        return currentOrder;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (customerImage != null) {
            g.drawImage(customerImage, 0, 60, getWidth() - 80, getHeight() - 60, this);
        }

        if (chatImage != null) {
            int chatWidth = 300;
            int chatHeight = 210;

            int chatX = 217;
            int chatY = 30;

            g.drawImage(chatImage, chatX, chatY, chatWidth, chatHeight, this);

            g.setColor(Color.BLACK);
            g.setFont(new Font("Tahoma", Font.BOLD, 35));

            FontMetrics metrics = g.getFontMetrics(g.getFont());
            int textWidth = metrics.stringWidth(currentOrder);
            int textX = chatX + (chatWidth - textWidth) / 2 + 10;
            int textY = chatY + ((chatHeight - metrics.getHeight()) / 2) + metrics.getAscent() - 20;

            g.drawString(currentOrder, textX, textY);
        }
    }
}