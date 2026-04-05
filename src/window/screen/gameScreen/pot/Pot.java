package window.screen.gameScreen.pot;
import window.screen.gameScreen.GameScreenListener;
import window.screen.gameScreen.ingredient.Ingredient;

import javax.swing.*;
import java.awt.*;

public class Pot extends JComponent implements GameScreenListener {
    private Rectangle potZone;

    public Pot() {
        potZone = new Rectangle();
    }

    public Rectangle getPotZone(){
        return potZone;
    }

    @Override
    public void gameScreenResized(Dimension size){
        int x = (int)(size.width * 0.45);
        int y = (int)(size.height * 0.79);
        int w = (int)(size.width * 0.09);
        int h = (int)(size.height * 0.05);
        potZone = new Rectangle(x,y,w,h);
        setBounds(x,y,w,h);
    }


}
