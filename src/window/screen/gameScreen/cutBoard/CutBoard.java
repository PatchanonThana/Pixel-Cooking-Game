package window.screen.gameScreen.cutBoard;

import window.screen.gameScreen.GameScreenListener;

import javax.swing.*;
import java.awt.*;

public class CutBoard extends JComponent implements GameScreenListener {
    Rectangle boardZone;
    public CutBoard(){
        boardZone = new Rectangle();
    }

    public Rectangle getBoardZone(){
        return boardZone;
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
