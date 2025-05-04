import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;


public class Enemy {
	private int x, y; // 敵人的位置
	private int dx = 2; // 水平移動速度
	private static final int WIDTH = 20; // 敵人寬度
	private static final int HEIGHT = 20; // 敵人高度
	private static final int DROP = 20; // 每次撞到邊界掉落的距離
	private static final int FIRE_CHANCE = 20; // 發射機率（百分比）
	private Random random = new Random();
	private Timer timer;
	private int d;
	int moveCounter = 0;
	private int moveInterval; // 每隻敵人有不同間隔
	private int direction;
    
	public Enemy(int x, int y) {
	    this.x = x;
	    this.y = y;
	    this.direction = (int)(Math.random() * 4); // 0~3 隨機方向
	    this.moveInterval = 300 + (int)(Math.random() * 100); // 每幾幀移動
	    this.moveCounter = 0;
	}

	static {
		
	}
	public void move_up() {
		 y -= dx;
		 if (y  < 0) {
				dx = -dx;
			}
		 if (y + HEIGHT > Setting.panelHeight) {
				dx = -dx;
			}
	}
	public void move_down() {
		y += dx;
		 if (y  < 0) {
				dx = -dx;
			}
		 if (y + HEIGHT > Setting.panelHeight) {
				dx = -dx;
			}
	}
	public void move_left() {
		x -= dx;
		if (x < 0) {
			dx = -dx;
		}
		if (x + WIDTH > Setting.panelWidth) {
			dx = -dx;
		}
	}
	public void move_right() {
		x += dx;
		if (x < 0) {
			dx = -dx;
		}
		if (x + WIDTH > Setting.panelWidth) {
			dx = -dx;
		}
	}
	
	public void randomMove() {
	    moveCounter++;
//	    System.out.println("--------------");
//	    System.out.println(moveCounter);
//	    System.out.println(moveInterval);
//	    System.out.println("--------------");
	    if (moveCounter >= moveInterval) {
	        direction = (int)(Math.random() * 4); // 每次更新方向
	        moveCounter = 0;
	    }
	    if (direction == 0)
	    	move_up();
	    if (direction == 1)
	    	move_down();
	    if (direction == 2)
	    	move_left();
	    if (direction == 3)
	    	move_right();
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