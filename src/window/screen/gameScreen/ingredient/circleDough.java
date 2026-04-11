package window.screen.gameScreen.ingredient;

import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.pot.Pot;

//ขนมวงดิบ class แยก
public class circleDough extends Ingredient {
    public circleDough(double relX, double relY, Pot pot, CutBoard cutBoard){
        super("/dessert/ขนมวง1.png", relX , relY,0.010,0.012,pot , cutBoard);

    }
}
