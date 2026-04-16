package window.screen.gameScreen.ingredient;

import window.screen.gameScreen.cutBoard.CutBoard;
import window.screen.gameScreen.pot.Pot;

//ขนมวงดิบ class แยก
public class circleDough extends Ingredient {
    public circleDough(double relX, double relY, Pot pot, CutBoard cutBoard){
        super("/dessert/ring1.png", relX , relY,0.010,0.012,pot, cutBoard, Type.FOOD);

        // set สถานะและชนิดขนม
        setPrepState(PrepState.RING);
        setFoodKind(FoodKind.RING);

    }
}
