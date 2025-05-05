package gameObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.Timer;

import panelRelated.Setting;

public class Square extends Enemy{
	
	private int dx, dy; // æ°´å¹³ç§»å‹•é€Ÿåº¦
	public static final int WIDTH = 30; // æ•µäººå¯¬åº¦
	private boolean dir;

	public Square() {
		health = 10;
		x = -1 * WIDTH / 2 + Setting.PANEL_WIDTH * (int) (Math.random() * 2);
		y = 100;
		if(x < 0) {
			dir = true;
			dx = 8;
		}else {
			dir = false;
			dx = -8;
		}
		dy = 1;
	}
	
	public void move() {
		if(x + WIDTH >= Setting.PANEL_WIDTH && dir) {
			dx *= -1;
			dir = !dir;
		}
		if(x <= 0 && !dir) {
			dx *= -1;
			dir = !dir;
		}
		x += dx;
		y += dy;
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, WIDTH, WIDTH);
	}
	
    public boolean isOutOfScreen() {
        return y + WIDTH < 0;
    }
	// ç¹ªè£½æ•µäºº
	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.RED);
		g2d.fillRect(x, y, WIDTH, WIDTH);
		g2d.setColor(Color.black);
		g2d.drawRect(x, y, WIDTH, WIDTH);
	}

	@Override
	public void gotDamaged() {
		health--;
	}

	@Override
	public boolean alive() {
		return health > 0;
	}

}
