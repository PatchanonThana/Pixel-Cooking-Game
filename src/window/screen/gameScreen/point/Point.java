package window.screen.gameScreen.point;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Point {
    private static Point instance;
    private int totalScore;

    public Point() {
        instance = this;
        this.totalScore = 0; // เริ่มต้นที่ 0000
    }

    public static Point getInstance() {
        return instance;
    }

    public void processService(String customerOrder, String playerFood) {
        // ตรวจสอบว่าสิ่งที่ส่ง ตรงกับที่ลูกค้าสั่งไหม
        if (customerOrder.equals(playerFood)) {
            calculateAddScore(playerFood);
        } else {
            // ส่งผิด หัก 50 คะแนน
            this.totalScore -= 50;
            if (this.totalScore < 0) this.totalScore = 0; // ไม่ให้คะแนนติดลบ
        }
    }

    // ระบบคำนวณคะแนนแต่ละเมนู
    private void calculateAddScore(String foodName) {
        switch (foodName) {
            case "ขนมวง":
                this.totalScore += 100;
                break;
            case "ขนมเทียน":
                this.totalScore += 120;
                break;
            case "ขนมแคบ":
                this.totalScore += 80;
                break;
        }
        System.out.println("คะแนนปัจจุบันคือ: " + this.totalScore);
    }

    public void draw(Graphics2D g2, int screenWidth) {
        // 1. ตั้งค่า Font และสี
        g2.setFont(new Font("Monospaced", Font.BOLD, 35));
        g2.setColor(Color.WHITE);

        // 2. จัดรูปแบบข้อความ 0000
        String scoreText = "SCORE:" + String.format("%04d", totalScore);

        // 3. คำนวณตำแหน่ง
        int x = screenWidth - 250;
        int y = 50;

        // วาดเงาให้เห็นชัด
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(scoreText, x + 2, y + 2);

        // วาดตัวเลขจริง
        g2.setColor(Color.WHITE);
        g2.drawString(scoreText, x, y);
    }

    // เผื่อต้องใช้ค่าคะแนนไปทำหน้า GameOver
    public int getTotalScore() {
        return totalScore;
    }
}
