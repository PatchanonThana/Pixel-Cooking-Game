package window.screen.gameScreen.customer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import javax.swing.*;
import java.awt.FontMetrics;

public class Customer extends JPanel {
    String name;
    Image image;
    String orderedMenu;


    private static final String[] CUSTOMER_NAMES = {"ลูกค้า A", "ลูกค้า B", "ลูกค้า C"};
    private static final String[] CUSTOMER_IMAGES = {
            "src/window/screen/gameScreen/customer/Sprite-0001.png",
            "src/window/screen/gameScreen/customer/Sprite-0002.png",
            "src/window/screen/gameScreen/customer/Sprite-0004.png"
    };
    private static final String[] MENUS = {"ขนมวง", "ข้าวหนุกงา", "ขนมเทียน"};


    public Customer(int startX, int startY) {
        Random random = new Random();

        int randomCustomerIndex = random.nextInt(CUSTOMER_NAMES.length);
        this.name = CUSTOMER_NAMES[randomCustomerIndex];

        this.image = new ImageIcon(CUSTOMER_IMAGES[randomCustomerIndex]).getImage();

        int randomMenuIndex = random.nextInt(MENUS.length);
        this.orderedMenu = MENUS[randomMenuIndex];

        setOpaque(false); // กำหนดให้พื้นหลังโปร่งใส
        setBounds(startX , startY, 300, 480);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int boxWidth = 150;
        int boxHeight = 50;
        int boxX = 75;

        g.setColor(Color.WHITE);
        g.fillRect(boxX, 0, boxWidth, boxHeight);

        g.setColor(Color.BLACK);
        g.drawRect(boxX, 0, boxWidth, boxHeight);

        Font font= new Font("Tahoma", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.BLACK);

        FontMetrics metrics = g.getFontMetrics(font);

        int textWidth = metrics.stringWidth(orderedMenu);
        int textX = boxX + (boxWidth - textWidth) / 2;
        int textY = ((boxHeight - metrics.getHeight()) / 2) + metrics.getAscent();

        g.drawString(orderedMenu, textX, textY);

        if (image != null) {
            g.drawImage(image, 0, 80, 300, 400, null);
        }
    }

    public String getOrderedMenu() {
        return orderedMenu;
    }

    public String getName() {
        return name;
    }
}