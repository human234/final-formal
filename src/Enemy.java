import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;


public class Enemy {
	private int y = 40; // 敵人y座標
	private int x;// 敵人x座標(隨機)
	private int dx = (int) (1 + (Math.random() * 2)); // 水平移動速度(1~2)
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
	    this.direction = (int)(Math.random() * 8); // 0~3 隨機方向
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
	    System.out.println(direction);
	    if (moveCounter >= moveInterval) {
	        direction = (int)(Math.random() * 8); // 每次更新方向
	        moveCounter = 0;
	    }
	    if (direction == 0)//向上
	    	move_up();
	    if (direction == 1)//向下
	    	move_down();
	    if (direction == 2)//向左
	    	move_left();
	    if (direction == 3)//向右
	    	move_right();
	    if (direction == 4){//向右上
	    	move_up();
	    	move_right();
	    }
	    if (direction == 5){//向右下
	    	move_down();
	    	move_right();
	    }
	    if (direction == 6){//向左上
	    	move_up();
	    	move_left();
	    }
	    if (direction == 7){//向左下
	    	move_down();
	    	move_left();
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