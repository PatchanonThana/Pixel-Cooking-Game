package window.screen.gameScreen.backgrond;

import window.screen.gameScreen.GameScreenListener;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Background extends JPanel implements GameScreenListener {

    final private Image backgroundImg;
    private Dimension thisSize;

    public  Background() {
        backgroundImg = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/screenImage/Mainscreen.png"))
        ).getImage();
    }

    @Override
    public void gameScreenResized(Dimension size) {

        thisSize = new Dimension(size.width,size.height);

        setBounds(
          0,
          0,
          size.width,
          size.height
        );
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImg,0,0,thisSize.width,thisSize.height,this);
    }
}
