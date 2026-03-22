package window.screen.gameScreen.CutBoard;

import javax.swing.*;
import java.awt.*;

public class CutBoard extends JComponent {
    Rectangle boardZone;
    public CutBoard(){
        boardZone = new Rectangle(1190,685,75,100);
    }

    public Rectangle getBoardZone(){
        return boardZone;
    }
}
