package window.screen.gameScreen.ingredient;

import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.pot.Pot;

public class circleDough extends Ingredient {
    public circleDough(double relX, double relY, Pot pot, CutBoard cutBoard){
        super("/dessert/ขนมวง1.png", relX , relY,16,11,pot , cutBoard);

    }
}
