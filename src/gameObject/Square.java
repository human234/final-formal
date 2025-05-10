package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import abstract_interface.Enemy;
import panelRelated.Setting;

public class Square extends Enemy {

	private int dx;
	private double dy, dy_culmu;
	public static final int WIDTH = 30, HEIGHT = 30;
	private boolean dir;

	public Square() {
		health = 10;
		x = -1 * WIDTH / 2 + Setting.PANEL_WIDTH * (int) (Math.random() * 2);
		y = 100;
		dx = 8;
		dir = true;
		if(x > Setting.PANEL_WIDTH) {
			dir = !dir;
			dx *= -1;
		}
		dy = 0.9;
	}

	public void act() {
		if (x + WIDTH / 2 >= Setting.PANEL_WIDTH && dir) {
			dx *= -1;
			dir = !dir;
		}
		if (x <= WIDTH / 2 && !dir) {
			dx *= -1;
			dir = !dir;
		}
		if (dy_culmu < 1) {
			dy_culmu += dy;
		} else {
			y += 1;
			dy_culmu -= 1;
		}
		x += dx;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.RED);
		g2d.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
		g2d.setColor(Color.black);
		g2d.drawRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
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
