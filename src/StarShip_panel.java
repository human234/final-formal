import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StarShip_panel extends JPanel implements ActionListener, KeyListener {
    private int shipX = 400;
    private int shipY = 500;
    private int shipSpeed = 10;
    private Timer timer;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public StarShip_panel() {
        setFocusable(true);
        addKeyListener(this);
        timer = new Timer(30, this); // 每30ms更新一次
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);

        // 畫飛船
        g.setColor(Color.CYAN);
        g.fillRect(shipX, shipY, 50, 30);

        // 畫子彈
        g.setColor(Color.YELLOW);
        for (Bullet bullet : bullets) {
            g.fillRect(bullet.x, bullet.y, 5, 10);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 子彈移動
        for (Bullet bullet : bullets) {
            bullet.y -= 10;
        }
        // 刪掉超出畫面的子彈
        bullets.removeIf(bullet -> bullet.y < 0);

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT && shipX > 0) {
            shipX -= shipSpeed;
        }
        if (code == KeyEvent.VK_RIGHT && shipX < getWidth() - 50) {
            shipX += shipSpeed;
        }
        if (code == KeyEvent.VK_SPACE) {
            bullets.add(new Bullet(shipX + 22, shipY)); // 在飛船中間發射
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    class Bullet {
        int x, y;
        Bullet(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}