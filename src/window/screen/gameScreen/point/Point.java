package window.screen.gameScreen.point;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.*;
import java.util.Properties;

public class Point {
    private static Point instance;
    private int totalScore;
    private int highestScore;

    private final String filePath = "src/player/data.properties";

    public Point() {
        instance = this;
        this.totalScore = 0;
        loadHighScore();
    }

    public static Point getInstance() {
        return instance;
    }


    public void processService(String customerOrder, String playerFood, int timeLeft) {
        //ตรวจสอบว่าสิ่งที่ส่ง ตรงกับที่ลูกค้าสั่งไหม
        if (customerOrder.equals(playerFood)) {
            //เพิ่มคะแนนตามเมนู + โบนัสเวลา
            calculateAddScore(playerFood, timeLeft);
        } else {
            //ส่งผิด หัก 50 คะแนน
            this.totalScore -= 50;
            if (this.totalScore < 0) this.totalScore = 0; // ไม่ให้คะแนนติดลบ
        }
        if (this.totalScore > this.highestScore) {
            this.highestScore = this.totalScore;
            saveHighScore();
        }
    }

    //กรณีส่งไม่ทัน หัก 30 คะแนน
    public void timeOutPunish() {
        this.totalScore -= 30;
        if (this.totalScore < 0)
            this.totalScore = 0;
    }

    // ระบบคำนวณคะแนนแต่ละเมนู
    private void calculateAddScore(String foodName, int timeBonus) {
        int foodscore = 0;
        switch (foodName) {
            case "ขนมวง":
                foodscore += 100;
                break;
            case "ขนมเทียน":
                foodscore += 120;
                break;
            case "ขนมแคบ":
                foodscore += 80;
                break;
        }
        this.totalScore += (foodscore + timeBonus);
        if (this.totalScore > this.highestScore) {
            this.highestScore = this.totalScore;
            saveHighScore();
        }
        System.out.println("คะแนนปัจจุบันคือ: " + this.totalScore);
    }

    //ระบบจัดการไฟล์
    private void loadHighScore() {
        Properties prop = new Properties();
        try (FileInputStream in = new FileInputStream(filePath)) {
            prop.load(in);
            // ดึงค่า PlayerHighestScore มา ถ้าไม่มีให้เป็น 0
            String savedScore = prop.getProperty("PlayerHighestScore", "0");
            this.highestScore = Integer.parseInt(savedScore);
        } catch (IOException | NumberFormatException e) {
            this.highestScore = 0; // ถ้าไฟล์ไม่มี หรือ Error ให้เริ่มที่ 0
        }
    }

    private void saveHighScore() {
        Properties prop = new Properties();
        File file = new File(filePath);

        //โหลดข้อมูลเดิมก่อน
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                prop.load(in);
            } catch (IOException e) {
                System.out.println("Error loading properties: " + e.getMessage());
            }
        }

        //ดึงคะแนนเก่าในไฟล์มาดู (ถ้าไม่มีให้เป็น 0)
        int oldHighScoreInFile = Integer.parseInt(prop.getProperty("PlayerHighestScore", "0"));

        // เช็คก่อนว่าคะแนนใหม่ > คะแนนเก่ามั้ย
        if (this.highestScore > oldHighScoreInFile) {
            // ถ้าจริงค่อยเซตค่าใหม่และเขียนทับ
            prop.setProperty("PlayerHighestScore", String.valueOf(this.highestScore));
            try (FileOutputStream out = new FileOutputStream(file)) {
                prop.store(out, "New High Score Saved!");
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        //ถ้าคะแนนน้อยกว่าจะไม่ทำอะไรเลย
    }

    public void draw(Graphics2D g2, int screenWidth) {
        //วาดคะแนนปัจจุบัน
        //ตั้งค่า Font และสี
        g2.setFont(new Font("Monospaced", Font.BOLD, 35));
        g2.setColor(Color.WHITE);
        //จัดรูปแบบข้อความ 0000
        String scoreText = "SCORE :" + String.format("%04d", totalScore);
        //คำนวณตำแหน่ง
        int x = screenWidth - 250;
        int y = 50;
        //วาดเงาให้เห็นชัด
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(scoreText, x + 2, y + 2);
        //วาดตัวเลขจริง
        g2.setColor(Color.WHITE);
        g2.drawString(scoreText, x, y);

        //วาดhighest score
        g2.setFont(new Font("Monospaced", Font.BOLD, 20));
        String highestText = "HIGHESTSCORE :" + String.format("%04d", highestScore);
        int bestY = y + 30;
        // วาดเงาจางๆ
        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(highestText, x + 1, bestY + 1);
        // วาดตัวหนังสือสีขาวแบบโปร่งแสงนิดๆ
        g2.setColor(new Color(255, 255, 255,220));
        g2.drawString(highestText, x, bestY);
    }

    //เผื่อต้องใช้ค่าคะแนนไปทำหน้า GameOver
    public int getTotalScore() {
        return totalScore;
    }
}
