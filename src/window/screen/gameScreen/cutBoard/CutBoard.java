package window.screen.gameScreen.cutBoard;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Dough;
import window.screen.gameScreen.ingredient.Ingredient;
import window.screen.gameScreen.pot.Pot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CutBoard extends JComponent implements GameScreenListener {
    Rectangle boardZone;
    Pot pot;
    CutBoard cutBoard;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();


    public CutBoard(){
        boardZone = new Rectangle();
    }

    public Rectangle getBoardZone(){
        return boardZone;
    }

    //นำวัตถุดิบเข้าเขียงและเรียกใช้งานสูตร
    public void addIngredient(Ingredient ingredient){
        ingredients.add(ingredient);
        checkRecipe();
    }

    //เช็คสูตร รับค่าของวัตถุที่ถูกใส่ในลิสและนำมาเช็ค boolean
    public void checkRecipe(){
        boolean hasDough = ingredients.stream().anyMatch(i-> i instanceof Dough);
        //**หมายเหตุว่าใช้เป็นpathไฟล์เพื่อลองระบบ อาจจะเพิ่มชื่อให้เรียกใช้งานง่ายขึ้นในอนาคต
        boolean hasFilling = ingredients.stream().anyMatch(i-> i.getFilename().contains("/equipment/filling.png"));
        boolean hasSesame = ingredients.stream().anyMatch(i -> i.getFilename().contains("equipment/sesame.png"));

        //อันนี้สูตรสำหรับ ขนมเทียนห่อไส้ยังไม่นึ่ง
        if(hasDough&&hasFilling){
            for(Ingredient i : ingredients){
                i.returnToStart();
            }
            ingredients.clear();
            Ingredient DoughandFilling = new Ingredient("/dessert/ขนมเทียน1.png" , 0.76,0.80,0.009,0.010,pot);
            JLayeredPane gameLayer = (JLayeredPane) getParent();
            gameLayer.add(DoughandFilling , JLayeredPane.DRAG_LAYER);
            DoughandFilling.gameScreenResized(gameLayer.getSize());
            gameLayer.revalidate();
            gameLayer.repaint();
        }

        //สูตรข้าวแคบดิบ จริงๆมันน่าจะย่อได้ แต่ค่อยแก้ ฮุฮิ
        if(hasDough&&hasSesame){
            for(Ingredient i : ingredients){
                i.returnToStart();
            }
            ingredients.clear();
            Ingredient DoughandSesame = new Ingredient("/dessert/ข้าวแคบ1.png" , 0.76,0.80,0.010,0.012,pot);
            JLayeredPane gameLayer = (JLayeredPane) getParent();
            gameLayer.add(DoughandSesame , JLayeredPane.DRAG_LAYER);
            DoughandSesame.gameScreenResized(gameLayer.getSize());
            gameLayer.revalidate();
            gameLayer.repaint();
        }
    }
    @Override
    public void gameScreenResized(Dimension size){
        int x = (int)(size.width * 0.77);
        int y = (int)(size.height * 0.79);
        int w = (int)(size.width * 0.04);
        int h = (int)(size.height * 0.11);
        boardZone = new Rectangle(x,y,w,h);
        setBounds(x,y,w,h);
    }
}
