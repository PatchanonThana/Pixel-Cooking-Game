package window.screen.gameScreen.pot;

import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.ingredient.Ingredient.PrepState;
import window.screen.gameScreen.ingredient.Ingredient.FoodKind;
import window.screen.gameScreen.ingredient.Ingredient.Type;
import window.soundPlayer.boilSoundPlayer.BoilSoundPlayer;
import window.soundPlayer.frySoundPlayer.FryCapSoundPlayer;
import window.soundPlayer.frySoundPlayer.FrySoundPlayer;
import window.soundPlayer.handSoundPlayer.HandSoundPlayer;
import window.soundPlayer.oilSoundPlayer.OilSoundPlayer;
import window.soundPlayer.potSoundPlayer.PotSoundPlayer;
import window.soundPlayer.pourWaterSoundPlayer.PourWaterSoundPlayer;
import window.soundPlayer.streamSoundPlayer.StreamSoundPlayer;
import window.soundPlayer.sugarSoundPlayer.SugarSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Pot extends JComponent implements GameScreenListener {
    enum State { EMPTY, WATER, OIL }

    //หม้อพื้นฐาน
    private Image emptyImage;
    private Image waterImage;
    private Image oilImage;
    private Image sugarImage;

    //หม้อ + ซึ้ง (นึ่ง)
    private Image steamerMidImage;
    private Image steamerFoodImage;
    private Image steamerTopImage;

    private Image currentImage;

    private State state = State.EMPTY;
    private boolean hasSteamerMid = false;
    private boolean hasSteamerTop = false;
    private boolean hasSyrup = false;

    private Ingredient currentFood;
    private boolean isCooking = false;
    private boolean waitingForSteammerTop = false;
    private boolean isSteaming = false;

    private Timer timer;
    private Rectangle potZone;

    private CutBoard cutBoard;

    final private FrySoundPlayer frySoundPlayer;
    final private StreamSoundPlayer streamSoundPlayer;
    final private BoilSoundPlayer boilSoundPlayer;
    final private PourWaterSoundPlayer pourWaterSoundPlayer;
    final private HandSoundPlayer handSoundPlayer;
    final private PotSoundPlayer potSoundPlayer;
    final private SugarSoundPlayer sugarSoundPlayer;
    final private FryCapSoundPlayer fryCapSoundPlayer;
    final private OilSoundPlayer oilSoundPlayer;

    public Pot() {
        potZone = new Rectangle();

        emptyImage = loadImage("/equipment/emptypot.png");
        waterImage = loadImage("/equipment/waterpot.png");
        oilImage = loadImage("/equipment/oilpot.png");
        sugarImage = loadImage("/equipment/sugarpot.png");

        steamerMidImage = loadImage("/equipment/steamermidpot.png");
        steamerFoodImage = loadImage("/equipment/thianpot.png");
        steamerTopImage = loadImage("/equipment/steamertoppot.png");

        currentImage = emptyImage;

        frySoundPlayer = new FrySoundPlayer();
        streamSoundPlayer = new StreamSoundPlayer();
        boilSoundPlayer = new BoilSoundPlayer();
        pourWaterSoundPlayer = new PourWaterSoundPlayer();
        handSoundPlayer = new HandSoundPlayer();
        potSoundPlayer = new PotSoundPlayer();
        sugarSoundPlayer = new SugarSoundPlayer();
        fryCapSoundPlayer = new FryCapSoundPlayer();
        oilSoundPlayer = new OilSoundPlayer();
    }

    private Image loadImage(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Image not found: " + path);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    public Rectangle getPotZone() {
        return potZone;
    }

    private void findCutBoard() {
        if (cutBoard != null) return;
        JLayeredPane gameLayer = (JLayeredPane) getParent();
        if (gameLayer == null) return;

        for (Component c : gameLayer.getComponents()) {
            if (c instanceof CutBoard cb) {
                cutBoard = cb;
                break;
            }
        }
    }

    private void updatePotImage() {
        if (state == State.EMPTY) {
            currentImage = emptyImage;
        } else if (state == State.OIL) {
            currentImage = oilImage;
        } else if (state == State.WATER) {
            if (hasSteamerMid && hasSteamerTop && currentFood != null) {
                currentImage = steamerTopImage;
            } else if (hasSteamerMid && currentFood != null) {
                currentImage = steamerFoodImage;
            } else if (hasSteamerMid) {
                currentImage = steamerMidImage;
            } else if (hasSyrup) {
                currentImage = sugarImage;
            } else {
                currentImage = waterImage;
            }
        }
        repaint();
    }

    public boolean addIngredient(Ingredient ing) {
        if (!potZone.intersects(ing.getBounds())) return false;

        Type type = ing.getType();

        if (type == Type.WATER) {
            resetPot();
            state = State.WATER;
            ing.returnToStart();
            pourWaterSoundPlayer.playSound();
            updatePotImage();
            return true;
        }

        if (type == Type.OIL) {
            resetPot();
            state = State.OIL;
            ing.returnToStart();
            oilSoundPlayer.playSound();
            updatePotImage();
            return true;
        }

        if (type == Type.SUGAR) {
            if (state != State.WATER) return false;
            ing.returnToStart();
            sugarSoundPlayer.playSound();
            startSyrupCooking();
            return true;
        }

        if (type == Type.STEAMERMID) {
            if (state != State.WATER) return false;
            hasSteamerMid = true;
            ing.returnToStart();
            potSoundPlayer.playSound();
            updatePotImage();
            return true;
        }

        if (type == Type.STEAMERTOP) {
            if (state != State.WATER) return false;
            if (!hasSteamerMid) return false;
            if (!waitingForSteammerTop) return false;

            hasSteamerTop = true;
            ing.returnToStart();
            potSoundPlayer.playSound();
            updatePotImage();
            startSteaming();
            streamSoundPlayer.playSound();
            return true;
        }

        if (type == Type.FOOD) {
            return handleFood(ing);
        }

        return false;
    }

    private boolean handleFood(Ingredient ing) {
        if (currentFood != null || isCooking) return false;

        FoodKind kind = ing.getFoodKind();
        PrepState prep = ing.getPrepState();

        //ทอด
        if (state == State.OIL) {
            if (kind == FoodKind.RING && prep == PrepState.RING) {
                putFoodInPot(ing, false);
                startCooking(PrepState.FRIED, "/dessert/ring2.png", 2000);
                frySoundPlayer.playSound();

                return true;
            }
            if (kind == FoodKind.KHAEB && prep == PrepState.WITH_SESAME) {
                putFoodInPot(ing, false);
                startCooking(PrepState.FRIED, "/dessert/khaeb2.png", 2000);
                fryCapSoundPlayer.playSound();
                return true;
            }
            return false;
        }

        //วางขนมบนซึ้ง (รอปิดฝา)
        if (state == State.WATER && hasSteamerMid && !hasSteamerTop) {
            if (kind == FoodKind.THIAN && prep == PrepState.WRAPPED) {
                putFoodInPot(ing, true);
                waitingForSteammerTop = true;
                handSoundPlayer.playSound();
                updatePotImage();
                return true;
            }
            return false;
        }

        //ชุบน้ำเชื่อม
        if (state == State.WATER && hasSyrup) {
            if (kind == FoodKind.RING && prep == PrepState.FRIED) {
                putFoodInPot(ing, false);
                startCooking(PrepState.COATED, "/dessert/ring3.png", 1500);
                boilSoundPlayer.playSound();
                return true;
            }
            return false;
        }

        return false;
    }

    private void putFoodInPot(Ingredient ing, boolean steaming) {
        currentFood = ing;
        isSteaming = steaming;
        ing.setVisible(false);
        repaint();
    }

    private void takeFoodOutOfPot() {
        findCutBoard();

        if (currentFood != null) {
            currentFood.setVisible(true);
            // วางขนมบนเขียง
            if (cutBoard != null) {
                Rectangle boardZone = cutBoard.getBoardZone();
                int newX = boardZone.x + (boardZone.width / 2) - (currentFood.getWidth() / 2);
                int newY = boardZone.y + (boardZone.height / 2) - (currentFood.getHeight() / 2);
                currentFood.setLocation(newX, newY);
            }
        }

        currentFood = null;
        isCooking = false;
        waitingForSteammerTop = false;
        isSteaming = false;

        if (hasSteamerTop) {
            hasSteamerMid = false;
            hasSteamerTop = false;
        }

        updatePotImage();
    }

    private void startSyrupCooking() {
        if (timer != null && timer.isRunning()) timer.stop();

        hasSyrup = false;
        updatePotImage();

        timer = new Timer(50, e -> {
            hasSyrup = true;
            updatePotImage();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void startSteaming() {
        if (timer != null && timer.isRunning()) timer.stop();

        isCooking = true;
        waitingForSteammerTop = false;

        timer = new Timer(3000, e -> {
            if (currentFood == null) return;
            currentFood.setPrepState(PrepState.STEAMED);
            currentFood.setImage("/dessert/tian3.png");
            takeFoodOutOfPot();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void startCooking(PrepState newState, String newImage, int cookTime) {
        if (timer != null && timer.isRunning()) timer.stop();

        isCooking = true;

        timer = new Timer(cookTime, e -> {
            if (currentFood == null) return;
            currentFood.setPrepState(newState);
            currentFood.setImage(newImage);
            takeFoodOutOfPot();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void resetPot() {
        state = State.EMPTY;
        hasSteamerMid = false;
        hasSteamerTop = false;
        hasSyrup = false;
        waitingForSteammerTop = false;
        isSteaming = false;

        if (currentFood != null) {
            currentFood.setVisible(true);
            currentFood.returnToStart();
        }
        currentFood = null;
        isCooking = false;

        if (timer != null && timer.isRunning()) timer.stop();
        updatePotImage();
    }

    @Override
    public void gameScreenResized(Dimension size) {
        // ขนาดรูปหม้อ
        int w = (int) (size.width * 0.15);
        int h = (int) (size.height * 0.15);
        int x = (size.width - w) / 2;
        int y = (int) (size.height * 0.75);
        setBounds(x, y, w, h);

        // ขนาด zone รัะบขนม
        int zoneW = (int) (w * 0.4);
        int zoneH = (int) (h * 0.05);
        int zoneX = x + (w - zoneW) / 2;
        int zoneY = y + (int) (h * 0.45);
        potZone = new Rectangle(zoneX, zoneY, zoneW, zoneH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (currentImage != null) {
            g2.drawImage(currentImage, 0, 0, getWidth(), getHeight(), this);
        }

        //วาดขนมลอยในหม้อ (ยกเว้นนึ่ง)
        if (currentFood != null && !isSteaming) {
            Image foodImg = currentFood.getFoodImage();
            if (foodImg != null) {
                int foodW = (int) (getWidth() * 0.6);
                int foodH = (int) (getHeight() * 0.6);
                int foodX = (getWidth() - foodW) / 2;
                int foodY = (int) (getHeight() * 0.2);

                g2.drawImage(foodImg, foodX, foodY, foodW, foodH, this);
            }
        }
    }
}