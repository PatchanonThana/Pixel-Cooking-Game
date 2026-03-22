package window.screen.gameScreen.pot;
import window.screen.gameScreen.ingredient.Ingredient;

import javax.swing.*;
import java.awt.*;

public class Pot extends JComponent {
    private Rectangle potZone;

    public Pot() {
        potZone = new Rectangle(700,685,150,50);
    }

    public Rectangle getPotZone(){
        return potZone;
    }


}
