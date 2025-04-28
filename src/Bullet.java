import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Bullet {
    private int x, y; // 子彈的位置
    private static final int WIDTH = 5; // 子彈寬度
    private static final int HEIGHT = 10; // 子彈高度
    private static final int SPEED = 10; // 子彈移動速度

    public Bullet(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
        y -= SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** 取得子彈的邊界矩形，用於碰撞檢測 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    /** 子彈是否已經飛出螢幕 */
    public boolean isOutOfScreen() {
        return y + HEIGHT < 0;
    }
    
    // 繪製子彈 
    public void drawShape(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(getX(), getY(), WIDTH, HEIGHT);
    }
}
