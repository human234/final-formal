package gameObject;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;


public class Bullet {
	public Color myColor;
	public int x, y, dy; // å­�å½ˆçš„ä½�ç½®
	public static final int WIDTH = 5; // å­�å½ˆå¯¬åº¦
	public static final int HEIGHT = 10; // å­�å½ˆé«˜åº¦

	public Bullet(int x, int y, int dy, Color color) {
		myColor = color;
		this.x = x;
		this.y = y;
		this.dy = dy;
	}

	public void move() {
		y += dy;
	}

	// å�–å¾—å­�å½ˆçš„é‚Šç•ŒçŸ©å½¢ï¼Œç”¨æ–¼ç¢°æ’žæª¢æ¸¬
	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	// å­�å½ˆæ˜¯å�¦å·²ç¶“é£›å‡ºèž¢å¹•
	public boolean isOutOfScreen() {
		return y + HEIGHT < 0;
	}

	// ç¹ªè£½å­�å½ˆ
	public void drawShape(Graphics g) {
		g.setColor(myColor);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}
