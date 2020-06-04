import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {

    private final int SIZE = 640;// размер поля в пикселях
    private final int DOT_SIZE = 16;//размер сечения змейки или яблочка в пикселях 16
    private final int ALL_DOTS = 1600;// всего сколько может поместиться на игровом поле точек с размером DOT_SIZE 320/16=20 b 20*20=400
    private Image dot;// реализация змейки
    private Image apple;// реализация яблока
    private int appleX;// позиция яблока по оси Х
    private int appleY;// позиция яблока по оси Y
    private int score;// количество съеденых яблок
    //Два массива для хранения всех положений змейки
    private int[] snakeX = new int[ALL_DOTS];
    private int[] snakeY = new int[ALL_DOTS];

    private int dots;// размер змейки в данный момент времени (начальный размер 3)
    private Timer timer;//стандартный свинговый таймер

    // пять полей типа boolean , которые будут отвечать за текущее направление змейки
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    private boolean inGame;// показывает состояние игры (продолжается или закончена)

    ScorePanel scorePanel;// переменная - окно результатов
    SoundGame sound;// переменная класса звук

    /**
     * Метод, устанавливающий значения состояния направления движения змейки
     */
    private void setValueBool() {
        left = false;
        right = true;
        up = false;
        down = false;
        inGame = true;

    }

    /**
     * Конструктор класса GameField
     */
    public GameField() {
        setValueBool ();// задали начальные значения направления движения змейки
        setBackground (Color.BLACK);//задали цвет игрового поля
        loadImages ();//загрузка картинок
        initGame ();//
        addKeyListener (new FieldKeyListener ());
        setFocusable (true);// взаимодействие клавиш фокусируется на игровом поле
        scorePanel = new ScorePanel (score);
        sound = new SoundGame ();
    }

    /**
     * метод , который инициализирует начало игры, т.е
     * - задает начальную длину и координаты положения змейки
     * - создает timer, который с задержкой 250 млСек запускает метод actionPerformed();
     * - запускает метод
     */

    public void initGame() {
        dots = 3;// начальная длина змейки 3 точки по 16 пикселей
//описываем координаты начального положения змейки цикл for
        for (int i = 0; i < dots; i++) {
            snakeX[i] = 48 - i * DOT_SIZE;
            snakeY[i] = 48;
        }
        timer = new Timer (250, this);// this - вызывает метод класса  java.awt.event.ActionListener listener
        timer.start ();
        createApple ();
    }

    /**
     * метод создания яблока
     * - задает координаты расположения яблока на поле
     */

    public void createApple() {
        appleX = new Random ().nextInt (19) * DOT_SIZE;
        appleY = new Random ().nextInt (19) * DOT_SIZE;

        for (int i = 0; i < dots; i++) {
            if (snakeX[i] == appleX && snakeY[i] == appleY) {
                appleX = new Random ().nextInt (19) * DOT_SIZE;
                appleY = new Random ().nextInt (19) * DOT_SIZE;
            }
        }

    }

    /**
     * метод для загрузки картинок
     * т.е. в переменную apple сохраняем картинку яблоко
     * а в переменную dot - одну точку змейки
     */
    public void loadImages() {
        ImageIcon iia = new ImageIcon ("Picture\\apple.png");
        apple = iia.getImage ();
        ImageIcon iid = new ImageIcon ("Picture\\dot.png");
        dot = iid.getImage ();

    }

    /**
     * метод который двигает змейку
     * в котором будет происходить логическая перерисовка точек,
     * т.е. они будут сдвинаться в том массиве который
     * мы задали для хранения позиций ячеек змейки
     */

    public void move() {

        //изменения позиции тела змейки
        for (int i = dots; i > 0; i--) {
            snakeX[i] = snakeX[i - 1];
            snakeY[i] = snakeY[i - 1];
        }

        //изменение позиции головы змейки
        if (left) {
            snakeX[0] -= DOT_SIZE;
        }
        if (right) {
            snakeX[0] += DOT_SIZE;
        }
        if (up) {
            snakeY[0] -= DOT_SIZE;
        }
        if (down) {
            snakeY[0] += DOT_SIZE;
        }
    }

    /**
     * Метод который проверяет не встретилось ли яблоко на пути змейки
     */

    public void checkApple() {
        if (snakeX[0] == appleX && snakeY[0] == appleY) {
            dots++;
            score++;
            sound.PlayMusicSDL (sound.getPath ());
            scorePanel.getLabel ().setText ("" + score + " шт");
            createApple ();
        }
    }

    /**
     * Метод, который отрисовывает наше игровое поле
     * вызывается автоматически системой при каждом обновлении изображения
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent (g);
        if (inGame) {
            g.drawImage (apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage (dot, snakeX[i], snakeY[i], this);
            }

        } else {
            String str = "Game Over!";
            //Font f = new Font ("Colibri", 300, Font.BOLD);
            g.setColor (Color.white);
            // g.setFont (f);
            g.drawString (str, 300, 200);
        }
    }


    /**
     * Метод, который проверяет на столкновение змейку с границами игрового поля и с самой собой
     */
    private void checkCollisions() {
        for (int i = dots; i > 0; i--) {
            if (i > 4 && snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                inGame = false;
            }
        }
        if (snakeX[0] > SIZE - DOT_SIZE * 2) {
            inGame = false;
        }
        if (snakeX[0] < 0) {
            inGame = false;
        }

        if (snakeY[0] > SIZE - DOT_SIZE * 2) {
            inGame = false;
        }
        if (snakeY[0] < 0) {
            inGame = false;
        }
    }

    /**
     * Метод определения правильного окончания в слове Яблоко в зависимости от из количества
     *
     * @param num
     * @return
     */
    private String getEnd(int num) {
        String end = null;
        int result = num;

        if (result < 10) {
            switch (result) {
                case 0:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    end = "";
                    break;
                case 1:
                    end = "о";
                    break;
                case 2:
                case 3:
                case 4:
                    end = "а";
                    break;
            }
        } else if (result > 9 && num <= 20) {
            end = "";
        } else {
            int lastNum = num - (result / 10) * 10;
            switch (lastNum) {
                case 0:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    end = "";
                    break;
                case 1:
                    end = "о";
                    break;
                case 2:
                case 3:
                case 4:
                    end = "а";
                    break;
            }
        }
        return end;
    }

    /**
     * Этот метод запускается каждый раз, когда таймер осчитывает 250 милисекунд
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (inGame) {
            checkApple ();
            checkCollisions ();
            move ();

        } else {
            timer.stop ();
            ImageIcon icon = new ImageIcon ("Picture\\apple_title.png");
            int selection = JOptionPane.showConfirmDialog (this, "Ваш счет: "
                            + score + " Яблок" + this.getEnd (score) + ".\n\n Продолжить Игру?",
                    "Окно сообщения", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, icon);

            if (selection == JOptionPane.YES_OPTION) {
                setValueBool ();
                score = 0;
                scorePanel.getLabel ().setText ("" + score + " шт");
                initGame ();

            } else {
                inGame = false;
                System.exit (0);
            }
        }
        repaint ();// метод перерисовки всех компонентов
    }

    /**
     * Класс обработки нажатия клавишь (внутренний)
     */
    class FieldKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed (e);
            int key = e.getKeyCode ();//определяем код клавиши, которая была нажата
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }

        }
    }
}

