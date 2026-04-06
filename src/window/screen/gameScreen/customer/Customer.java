package window.screen.gameScreen.customer;

import window.screen.gameScreen.GameScreenListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Customer extends JComponent implements GameScreenListener {

    private BufferedImage customerImage;
    private Image chatImage;
    private String currentOrder;

    private final String[] menuItems = {"ขนมวง", "ขนมเทียน", "ขนมแคบ"};
    private  int frameWidth;
    private  int frameHeight;

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
        frameWidth = customerImage.getWidth();
        frameHeight = customerImage.getHeight();

        setOpaque(false);
        setBounds(0,0,frameWidth * 9,frameHeight * 5);
    }

    // ฟังก์ชันสำหรับส่งค่าเมนูที่ลูกค้าสั่ง
    public String getCurrentOrder() {
        return currentOrder;
    }

    @Override
    public void gameScreenResized(Dimension size) {
        //ตำแหน่งตัวละคร
        System.out.println("width: " + size.getWidth());
        System.out.println("height" + size.getHeight());
        int responsiveX = (int) (size.width * 0.30);
        int responsiveY = (int) (size.height * 0.17);

        setLocation(responsiveX,responsiveY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //วาดรูปลูกค้าแบบล็อคขนาด
        if (customerImage != null) {
            int fixedCharWidth = customerImage.getWidth();
            int fixedCharHeight = customerImage.getHeight();
            g.drawImage(customerImage, 0, 0, fixedCharWidth*6, fixedCharHeight*6, this);
        }

        //วาดกล่องข้อความ
        if (chatImage != null) {
            int chatWidth = 400;
            int chatHeight = 280;
            //ตำแหน่งกล่อง
            int chatX = 250;
            int chatY = -50;

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





