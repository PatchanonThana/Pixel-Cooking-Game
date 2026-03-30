package window.screen.menuScreen.menuButton.exitButton;

import window.screen.menuScreen.MenuListener;
import window.soundPlayer.buttonSoundPlayer.ButtonSoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

import static java.awt.Cursor.HAND_CURSOR;

public class MenuExitButton extends JButton implements MenuListener {

    private final Dimension thisSize = new Dimension(600, 150);
    private final List<ExitButtonListener> exitButtonListeners;

    public MenuExitButton(List<ExitButtonListener> exitButtonSoundListener) {
        this.exitButtonListeners = exitButtonSoundListener;

        setCursor(Cursor.getPredefinedCursor(HAND_CURSOR));

        // --------- ปุ่มปกติ ---------
        ImageIcon rawExit = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/exitButton/exitImage/quitbutton.png"
        )));
        Image scaledExit = rawExit.getImage().getScaledInstance(
                thisSize.width, thisSize.height, Image.SCALE_SMOOTH
        );
        setIcon(new ImageIcon(scaledExit));

        // --------- ปุ่ม Hover ---------
        ImageIcon rawHoverExit = new ImageIcon(Objects.requireNonNull(getClass().getResource(
                "/window/screen/menuScreen/menuButton/exitButton/exitImage/quitbutton-Hover.png"
        )));
        Image scaledHoverExit = rawHoverExit.getImage().getScaledInstance(
                thisSize.width, thisSize.height, Image.SCALE_SMOOTH
        );
        setRolloverIcon(new ImageIcon(scaledHoverExit));

        // --------- FIX HOVER ขยับ ---------
        setMargin(new Insets(0, 0, 0, 0));   // กัน margin เปลี่ยน
        setBorder(null);                     // กัน border กระโดด
        setRolloverEnabled(true);

        setSize(thisSize);
        setPreferredSize(thisSize);
        setMinimumSize(thisSize);
        setMaximumSize(thisSize);

        setHorizontalAlignment(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.CENTER);

        // --------- UI ---------
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusPainted(false);

        // --------- Sound + Event ---------
        ButtonSoundPlayer buttonSoundPlayer = new ButtonSoundPlayer();

        addActionListener(e -> {
            buttonSoundPlayer.playSound();
            for (ExitButtonListener listener : exitButtonListeners) {
                listener.exitButtonClicked();
            }
        });
    }

    @Override
    public void menuResized(Dimension size) {
        setBounds(
                (int)(size.getWidth()/2 - thisSize.width/2.0),
                (int)(size.getHeight()/2 - thisSize.height/2.0) + 150, // ขยับลง
                thisSize.width,
                thisSize.height
        );
    }
}