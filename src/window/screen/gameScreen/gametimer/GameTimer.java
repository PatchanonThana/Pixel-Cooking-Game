package window.screen.gameScreen.gametimer;

import window.screen.gameScreen.GameScreenListener;
import javax.swing.*;
import java.awt.*;

public class GameTimer extends JComponent implements GameScreenListener {

    private int timeLeft = 150;
    private Timer timer;

    public static boolean isTimeUp = false;

    public GameTimer() {
        isTimeUp = false;

        //ให้นับถอยหลังทุกๆ1000มิลลิวินาที(1 วินาที)
        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
                repaint();
            } else {
                isTimeUp = true; // ล็อคเกม
                timer.stop();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        String timeText;
        if (isTimeUp) {
            g2.setFont(new Font("SansSerif", Font.BOLD, 60));
            g2.setColor(Color.RED);
            timeText = "Time’s up!";
        } else {
            g2.setFont(new Font("Monospaced", Font.BOLD, 35));
            g2.setColor(Color.WHITE);
            timeText = "TIME : " + String.format("%02d", timeLeft);
        }

        FontMetrics metrics = g2.getFontMetrics(g2.getFont());
        int textWidth = metrics.stringWidth(timeText);

        // ตำแหน่งเวลา
        int x = (getWidth() - textWidth) / 2;
        int y = 60;

        // วาดเงาสีดำ
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(timeText, x + 3, y + 3);

        // วาดข้อความเมื่อหมดเวลา
        g2.setColor(isTimeUp ? Color.RED : Color.WHITE);
        g2.drawString(timeText, x, y);
    }

    @Override
    public void gameScreenResized(Dimension size) {
        setBounds(0, 0, size.width, size.height);
    }
}