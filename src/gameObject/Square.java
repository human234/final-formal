package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.Timer;

import panelRelated.Setting;

public class Square extends Enemy {

	private int dx;
	private double dy;
	public static final int WIDTH = 30;
	private boolean dir;

	public Square() {
		health = 5;
		x = -1 * WIDTH / 2 + Setting.PANEL_WIDTH * (int) (Math.random() * 2);
		y = 100;
		if (x < 0) {
			dir = true;
			dx = 5;
		} else {
			dir = false;
			dx = -5;
		}
		dy = 0.15;
	}

	public void move() {
		if (x + WIDTH / 2 >= Setting.PANEL_WIDTH && dir) {
			dx *= -1;
			dir = !dir;
		}
		if (x <= WIDTH / 2 && !dir) {
			dx *= -1;
			dir = !dir;
		}
		if (dy < 1) {
			dy += 0.34;
		} else {
			y += 1;
			dy = 0;
		}
		x += dx;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - WIDTH / 2, WIDTH, WIDTH);
	}

	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.RED);
		g2d.fillRect(x - WIDTH / 2, y - WIDTH / 2, WIDTH, WIDTH);
		g2d.setColor(Color.black);
		g2d.drawRect(x - WIDTH / 2, y - WIDTH / 2, WIDTH, WIDTH);
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
