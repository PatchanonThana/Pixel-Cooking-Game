package window.screen.menuScreen.menuButton.startButton;

import window.screen.menuScreen.MenuListener;

import javax.swing.*;
import java.awt.*;


public class MenuStartButton extends JButton implements MenuListener {

    final private Dimension thisSize = new Dimension(100,30);
    MenuStartButtonListener startButtonListener;

    public MenuStartButton(MenuStartButtonListener startButtonListener){
        this.startButtonListener = startButtonListener;
        setText("start");
        addActionListener(e ->
                this.startButtonListener.menuStartButtonClicked()
        );
    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
                (int) (size.getWidth()/2 - thisSize.width/2.0),
                (int) (size.getHeight()/2 - thisSize.height/2.0) ,
                thisSize.width,
                thisSize.height
        );
    }
}