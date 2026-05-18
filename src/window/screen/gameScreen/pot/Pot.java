package window.screen.gameScreen.pot;

import java.awt.*;
import java.net.URL;
import java.util.Stack;
import javax.swing.*;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.ingredient.Ingredient.FoodKind;
import window.screen.gameScreen.ingredient.Ingredient.PrepState;
import window.soundPlayer.boilSoundPlayer.BoilSoundPlayer;
import window.soundPlayer.frySoundPlayer.FryCapSoundPlayer;
import window.soundPlayer.frySoundPlayer.FrySoundPlayer;
import window.soundPlayer.handSoundPlayer.HandSoundPlayer;
import window.soundPlayer.oilSoundPlayer.OilSoundPlayer;
import window.soundPlayer.potSoundPlayer.PotSoundPlayer;
import window.soundPlayer.pourWaterSoundPlayer.PourWaterSoundPlayer;
import window.soundPlayer.streamSoundPlayer.StreamSoundPlayer;
import window.soundPlayer.sugarSoundPlayer.SugarSoundPlayer;

public class Pot extends JComponent implements GameScreenListener {

    // ─── Enums ───────────────────────────────────────────────────────────────

    enum State { EMPTY, WATER, OIL }

    enum LayerType { STEAMER_MID, FOOD, STEAMER_TOP, SYRUP }

    // ─── Inner Classes ────────────────────────────────────────────────────────

    private static class PotLayer {
        LayerType type;
        Ingredient ingredient;

        PotLayer(LayerType type) {
            this.type = type;
        }

        PotLayer(LayerType type, Ingredient ingredient) {
            this.type = type;
            this.ingredient = ingredient;
        }
    }

    private class RightClickHandler extends java.awt.event.MouseAdapter {
        @Override
        public void mousePressed(java.awt.event.MouseEvent e) {
            if (e.getButton() == java.awt.event.MouseEvent.BUTTON3) {
                handleRightClick();
            }
        }
    }

    // ─── Images ───────────────────────────────────────────────────────────────

    private Image emptyImage;
    private Image waterImage;
    private Image oilImage;
    private Image sugarImage;
    private Image steamerMidImage;
    private Image steamerFoodImage;
    private Image steamerTopImage;
    private Image currentImage;

    // ─── State ────────────────────────────────────────────────────────────────

    private State state = State.EMPTY;
    private Stack<PotLayer> layers = new Stack<>();
    private Ingredient currentFood;
    private boolean isCooking  = false;
    private boolean isSteaming = false;
    private Timer timer;
    private Rectangle potZone;
    private CutBoard cutBoard;

    // ─── Sound Players ────────────────────────────────────────────────────────

    private FrySoundPlayer       frySoundPlayer;
    private StreamSoundPlayer    streamSoundPlayer;
    private BoilSoundPlayer      boilSoundPlayer;
    private PourWaterSoundPlayer pourWaterSoundPlayer;
    private HandSoundPlayer      handSoundPlayer;
    private PotSoundPlayer       potSoundPlayer;
    private SugarSoundPlayer     sugarSoundPlayer;
    private FryCapSoundPlayer    fryCapSoundPlayer;
    private OilSoundPlayer       oilSoundPlayer;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public Pot() {
        potZone = new Rectangle();
        initializePotImages();
        initializeSoundPlayers();
        currentImage = emptyImage;
        addMouseListener(new RightClickHandler());
    }

    // ─── Initialization ───────────────────────────────────────────────────────

    private void initializePotImages() {
        emptyImage       = loadImage("/equipment/emptypot.png");
        waterImage       = loadImage("/equipment/waterpot.png");
        oilImage         = loadImage("/equipment/oilpot.png");
        sugarImage       = loadImage("/equipment/sugarpot.png");
        steamerMidImage  = loadImage("/equipment/steamermidpot.png");
        steamerFoodImage = loadImage("/equipment/thianpot.png");
        steamerTopImage  = loadImage("/equipment/steamertoppot.png");
    }

    private void initializeSoundPlayers() {
        frySoundPlayer       = new FrySoundPlayer();
        streamSoundPlayer    = new StreamSoundPlayer();
        boilSoundPlayer      = new BoilSoundPlayer();
        pourWaterSoundPlayer = new PourWaterSoundPlayer();
        handSoundPlayer      = new HandSoundPlayer();
        potSoundPlayer       = new PotSoundPlayer();
        sugarSoundPlayer     = new SugarSoundPlayer();
        fryCapSoundPlayer    = new FryCapSoundPlayer();
        oilSoundPlayer       = new OilSoundPlayer();
    }

    // ─── Image Utility ────────────────────────────────────────────────────────

    private Image loadImage(String path) {
        URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Image not found: " + path);
            return null;
        }
        return new ImageIcon(url).getImage();
    }

    // ─── Public API ───────────────────────────────────────────────────────────

    public Rectangle getPotZone() {
        return potZone;
    }

    /**
     * Attempts to add an ingredient to the pot.
     * Returns true if the ingredient was accepted.
     */
    public boolean addIngredient(Ingredient ing) {
        if (!potZone.intersects(ing.getBounds())) return false;
        return switch (ing.getType()) {
            case WATER      -> addWater(ing);
            case OIL        -> addOil(ing);
            case SUGAR      -> addSugar(ing);
            case STEAMERMID -> addSteamerMid(ing);
            case STEAMERTOP -> addSteamerTop(ing);
            case FOOD       -> handleFood(ing);
            default         -> false;
        };
    }

    /**
     * Removes the top removable layer (FOOD or STEAMER_MID only).
     * SYRUP and STEAMER_TOP are managed by the system — not removable by player.
     */
    public void removeTopLayer() {
        if (layers.isEmpty()) return;

        Stack<PotLayer> temp = new Stack<>();
        PotLayer targetLayer = null;

        // ค้นหา layer บนสุดที่ผู้เล่นเอาออกได้
        while (!layers.isEmpty()) {
            PotLayer layer = layers.pop();
            if (targetLayer == null
                    && layer.type != LayerType.SYRUP
                    && layer.type != LayerType.STEAMER_TOP) {
                targetLayer = layer;
                break;
            }
            temp.push(layer);
        }

        // คืน layer ที่ข้ามไปกลับ stack ตามลำดับเดิม
        while (!temp.isEmpty()) {
            layers.push(temp.pop());
        }

        if (targetLayer == null) return;

        if (targetLayer.type == LayerType.FOOD
                && targetLayer.ingredient != null) {
            targetLayer.ingredient.setVisible(true);
            targetLayer.ingredient.returnToStart();
            currentFood = null;
            potSoundPlayer.playSound();
        } else if (targetLayer.type == LayerType.STEAMER_MID) {
            potSoundPlayer.playSound();
        }

        updatePotImage();
    }

    // ─── Layer Management (Private) ───────────────────────────────────────────

    /**
     * Removes a specific layer type from the stack regardless of position.
     * Used internally by the system (not triggered by player directly).
     */
    private void removeLayerByType(LayerType typeToRemove) {
        Stack<PotLayer> temp = new Stack<>();
        boolean found = false;

        while (!layers.isEmpty()) {
            PotLayer layer = layers.pop();
            if (!found && layer.type == typeToRemove) {
                if (layer.type == LayerType.FOOD
                        && layer.ingredient != null) {
                    layer.ingredient.setVisible(true);
                    layer.ingredient.returnToStart();
                    currentFood = null;
                }
                found = true;
            } else {
                temp.push(layer);
            }
        }

        while (!temp.isEmpty()) {
            layers.push(temp.pop());
        }

        updatePotImage();
    }

    private boolean containsLayer(LayerType type) {
        return layers.stream().anyMatch(l -> l.type == type);
    }

    private boolean hasSteamerMid() { return containsLayer(LayerType.STEAMER_MID); }
    private boolean hasSteamerTop() { return containsLayer(LayerType.STEAMER_TOP); }
    private boolean hasSyrup()      { return containsLayer(LayerType.SYRUP);       }
    private boolean hasFood()       { return containsLayer(LayerType.FOOD);        }

    /** True when strainer is placed and food is in, but lid is not yet closed. */
    private boolean isWaitingForSteamerTop() {
        return hasSteamerMid() && currentFood != null && !hasSteamerTop();
    }

    // ─── Add Ingredient Handlers ──────────────────────────────────────────────

    private boolean addWater(Ingredient ing) {
        resetPot();
        state = State.WATER;
        ing.returnToStart();
        pourWaterSoundPlayer.playSound();
        updatePotImage();
        return true;
    }

    private boolean addOil(Ingredient ing) {
        resetPot();
        state = State.OIL;
        ing.returnToStart();
        oilSoundPlayer.playSound();
        updatePotImage();
        return true;
    }

    private boolean addSugar(Ingredient ing) {
        if (state != State.WATER) return false;
        if (hasSteamerMid() && !hasSteamerTop()) return false;

        if (hasSyrup()) {
            removeLayerByType(LayerType.SYRUP);
            ing.returnToStart();
            potSoundPlayer.playSound();
            updatePotImage();
            return true;
        }

        ing.returnToStart();
        sugarSoundPlayer.playSound();
        layers.push(new PotLayer(LayerType.SYRUP));
        startSyrupCooking();
        return true;
    }

    private boolean addSteamerMid(Ingredient ing) {
        if (state != State.WATER) return false;
        if (hasSyrup()) return false;

        if (hasSteamerMid()) {
            removeLayerByType(LayerType.STEAMER_MID);
            ing.returnToStart();
            potSoundPlayer.playSound();
            updatePotImage();
            return true;
        }

        layers.push(new PotLayer(LayerType.STEAMER_MID));
        ing.returnToStart();
        potSoundPlayer.playSound();
        updatePotImage();
        return true;
    }

    private boolean addSteamerTop(Ingredient ing) {
        if (state != State.WATER)  return false;
        if (!hasSteamerMid())      return false;
        if (hasSteamerTop())       return false; // ล็อก: ระบบจัดการเอง ผู้เล่นเอาออกไม่ได้
        if (!isWaitingForSteamerTop()) return false;

        layers.push(new PotLayer(LayerType.STEAMER_TOP));
        ing.returnToStart();
        potSoundPlayer.playSound();
        updatePotImage();
        startSteaming();
        streamSoundPlayer.playSound();
        return true;
    }

    private boolean handleFood(Ingredient ing) {
        if (currentFood != null || isCooking) return false;

        FoodKind  kind = ing.getFoodKind();
        PrepState prep = ing.getPrepState();

        // ── Frying ────────────────────────────────────────────────────────────
        if (state == State.OIL) {
            if (kind == FoodKind.RING && prep == PrepState.RING) {
                putFoodInPot(ing, false);
                layers.push(new PotLayer(LayerType.FOOD, ing));
                startCooking(PrepState.FRIED, "/dessert/ring2.png", 2000);
                frySoundPlayer.playSound();
                return true;
            }
            if (kind == FoodKind.KHAEB && prep == PrepState.WITH_SESAME) {
                putFoodInPot(ing, false);
                layers.push(new PotLayer(LayerType.FOOD, ing));
                startCooking(PrepState.FRIED, "/dessert/khaeb2.png", 2000);
                fryCapSoundPlayer.playSound();
                return true;
            }
            return false;
        }

        // ── Steaming ──────────────────────────────────────────────────────────
        if (state == State.WATER && hasSteamerMid() && !hasSteamerTop()) {
            if (kind == FoodKind.THIAN && prep == PrepState.WRAPPED) {
                putFoodInPot(ing, true);
                layers.push(new PotLayer(LayerType.FOOD, ing));
                handSoundPlayer.playSound();
                updatePotImage();
                return true;
            }
            return false;
        }

        // ── Coating with syrup ────────────────────────────────────────────────
        if (state == State.WATER && hasSyrup()) {
            if (kind == FoodKind.RING && prep == PrepState.FRIED) {
                putFoodInPot(ing, false);
                layers.push(new PotLayer(LayerType.FOOD, ing));
                startCooking(PrepState.COATED, "/dessert/ring3.png", 1500);
                boilSoundPlayer.playSound();
                return true;
            }
            return false;
        }

        // ── Remove food if player drags cooked food out ───────────────────────
        if (currentFood == ing) {
            removeTopLayer();
            return true;
        }

        return false;
    }

    // ─── Food Placement ───────────────────────────────────────────────────────

    private void putFoodInPot(Ingredient ing, boolean steaming) {
        currentFood = ing;
        isSteaming  = steaming;
        ing.setVisible(false);
        repaint();
    }

    private void takeFoodOutOfPot() {
        findCutBoard();

        if (currentFood != null) {
            currentFood.setVisible(true);
            if (cutBoard != null) {
                Rectangle boardZone = cutBoard.getBoardZone();
                int newX = boardZone.x + (boardZone.width  / 2)
                        - (currentFood.getWidth()  / 2);
                int newY = boardZone.y + (boardZone.height / 2)
                        - (currentFood.getHeight() / 2);
                currentFood.setLocation(newX, newY);
            }
        }

        currentFood = null;
        isCooking   = false;
        boolean wasStea = isSteaming;
        isSteaming  = false;

        removeLayerByType(LayerType.FOOD);

        // หลังนึ่งเสร็จ ระบบเอาซึ้งและฝาออกเอง
        if (wasStea) {
            removeLayerByType(LayerType.STEAMER_TOP);
            removeLayerByType(LayerType.STEAMER_MID);
        }

        updatePotImage();
    }

    // ─── Right-Click Handler ──────────────────────────────────────────────────

    /**
     * คลิกขวา → เอาเลเยอร์ที่ผู้เล่นเอาออกได้ (FOOD / STEAMER_MID) ออกทีละชั้น
     * SYRUP และ STEAMER_TOP ระบบจัดการเอง ผู้เล่นเอาออกไม่ได้
     */
    private void handleRightClick() {
        if (layers.isEmpty()) return;
        if (isCooking) return; // ล็อกระหว่างปรุง

        // ตรวจว่ามี layer ที่เอาออกได้ไหม
        boolean hasRemovable = layers.stream().anyMatch(
                l -> l.type == LayerType.FOOD
                        || l.type == LayerType.STEAMER_MID
        );
        if (!hasRemovable) return;

        // Log layer บนสุดที่เอาออกได้
        for (int i = layers.size() - 1; i >= 0; i--) {
            PotLayer layer = layers.get(i);
            if (layer.type == LayerType.FOOD
                    || layer.type == LayerType.STEAMER_MID) {
                String name = (layer.type == LayerType.FOOD)
                        ? "Food" : "Strainer";
                System.out.println("Removing: " + name);
                break;
            }
        }

        removeTopLayer();
    }

    // ─── Cooking Timers ───────────────────────────────────────────────────────

    private void startSyrupCooking() {
        if (timer != null && timer.isRunning()) timer.stop();
        updatePotImage();
        timer = new Timer(50, e -> updatePotImage());
        timer.setRepeats(false);
        timer.start();
    }

    private void startSteaming() {
        if (timer != null && timer.isRunning()) timer.stop();
        isCooking = true;
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

    // ─── Pot Reset ────────────────────────────────────────────────────────────

    private void resetPot() {
        state = State.EMPTY;
        layers.clear();
        isSteaming = false;

        if (currentFood != null) {
            currentFood.setVisible(true);
            currentFood.returnToStart();
        }

        currentFood = null;
        isCooking   = false;

        if (timer != null && timer.isRunning()) timer.stop();
        updatePotImage();
    }

    // ─── Image Update ─────────────────────────────────────────────────────────

    private void updatePotImage() {
        currentImage = switch (state) {
            case EMPTY -> emptyImage;
            case OIL   -> oilImage;
            case WATER -> getWaterStateImage();
        };
        repaint();
    }

    private Image getWaterStateImage() {
        if (layers.isEmpty()) return waterImage;

        boolean hasMid   = hasSteamerMid();
        boolean hasTop   = hasSteamerTop();
        boolean hasFood  = hasFood();
        boolean hasSyrup = hasSyrup();

        if (hasMid && hasTop && hasFood) return steamerTopImage;
        if (hasMid && hasFood)           return steamerFoodImage;
        if (hasMid)                      return steamerMidImage;
        if (hasSyrup)                    return sugarImage;

        return waterImage;
    }

    // ─── CutBoard Lookup ──────────────────────────────────────────────────────

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

    // ─── Layout ───────────────────────────────────────────────────────────────

    @Override
    public void gameScreenResized(Dimension size) {
        int w = (int) (size.width  * 0.15);
        int h = (int) (size.height * 0.15);
        int x = (size.width - w) / 2;
        int y = (int) (size.height * 0.75);
        setBounds(x, y, w, h);

        int zoneW = (int) (w * 0.4);
        int zoneH = (int) (h * 0.05);
        int zoneX = x + (w - zoneW) / 2;
        int zoneY = y + (int) (h * 0.45);
        potZone = new Rectangle(zoneX, zoneY, zoneW, zoneH);
    }

    // ─── Painting ─────────────────────────────────────────────────────────────

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        if (currentImage != null) {
            g2.drawImage(currentImage, 0, 0, getWidth(), getHeight(), this);
        }

        if (currentFood != null && !isSteaming) {
            Image foodImg = currentFood.getFoodImage();
            if (foodImg != null) {
                int foodW = (int) (getWidth()  * 0.6);
                int foodH = (int) (getHeight() * 0.6);
                int foodX = (getWidth()  - foodW) / 2;
                int foodY = (int) (getHeight() * 0.2);
                g2.drawImage(foodImg, foodX, foodY, foodW, foodH, this);
            }
        }
    }
}