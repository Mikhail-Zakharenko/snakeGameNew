import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScorePanel extends JFrame {
    private Font font;
    private int num;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    private JLabel label;


    public ScorePanel(int score) {
        num = score;
        font = new Font (null, Font.ITALIC, 20);
        label = new JLabel ("" + num + " шт");
        ImageIcon icon = new ImageIcon ("Picture\\apple_title.png");
        setTitle ("Result");
        setDefaultCloseOperation (WindowConstants.DISPOSE_ON_CLOSE);
        setSize (200, 100);
        setLocation (990, 100);
        getContentPane ().setBackground (Color.ORANGE);
        label.setVerticalAlignment (JLabel.CENTER);
        label.setHorizontalAlignment (JLabel.CENTER);
        label.setHorizontalTextPosition (JLabel.RIGHT);
        label.setVerticalTextPosition (JLabel.CENTER);
        label.setIcon (icon);
        label.setFont (font);
       add (label);

        setVisible (true);
    }

}
