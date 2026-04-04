package window.screen.gameScreen.ingredient;

import window.screen.gameScreen.CutBoard.CutBoard;
import window.screen.gameScreen.pot.Pot;

public class circleDough extends Ingredient {
    public circleDough(int startX, int startY , Pot pot, CutBoard cutBoard){
        super("/dessert/ขนมวง1.png", startX , startY,16,11,pot , cutBoard);
    }
}
