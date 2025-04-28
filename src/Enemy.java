import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Enemy {
	private int x, y; // 敵人的位置
	private int dx = 2; // 水平移動速度
	private static final int WIDTH = 20; // 敵人寬度
	private static final int HEIGHT = 20; // 敵人高度
	private static final int DROP = 20; // 每次撞到邊界掉落的距離

	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
	}

	static {
		
	}
	public void move() {
		x += dx;
		if (x < 0) {
			x = 0;
			dx = -dx;
			y += DROP;
		} else if (x + WIDTH > Setting.panelWidth) {
			x = Setting.panelWidth - WIDTH;
			dx = -dx;
			y += DROP;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	// 繪製敵人
	public void drawShape(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(getX(), getY(), WIDTH, HEIGHT);
	}
}
