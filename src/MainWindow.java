import javax.swing.*;

public class MainWindow extends JFrame {
    GameField gameField;

    public MainWindow() {
        gameField = new GameField ();
        setTitle ("Snake");
        setDefaultCloseOperation (WindowConstants.EXIT_ON_CLOSE);
        setSize (640, 665);
        setLocation (350, 50);

        add (gameField);

        setVisible (true);
    }

    public static void main(String[] args) {
        MainWindow mainWindow = new MainWindow ();


        }
}

