package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;

import abstract_interface.Enemy;
import abstract_interface.Shottable;
import panelRelated.Setting;

public class Triangle extends Enemy implements Shottable {

	private int dx, count;
	private int attack;
	public static final int WIDTH = 20, HEIGHT = 20;

	public Triangle() {
		health = 3;
		count = 0;
		x = -1 * WIDTH / 2 + Setting.PANEL_WIDTH * (int) (Math.random() * 2);
		double reg = Math.random();
		if (reg < 0.33) {
			y = 30;
		} else if (reg < 0.66) {
			y = 70;
		} else {
			y = 110;
		}
	}

	@Override
	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.GRAY);
		int[] x_outline = { x - WIDTH / 2, x, x + WIDTH / 2 };
		int[] y_outline = { y - HEIGHT / 2, y + HEIGHT / 2, y - HEIGHT / 2 };
		g2d.fillPolygon(x_outline, y_outline, 3);
		g2d.setColor(Color.red);
		g2d.drawPolygon(x_outline, y_outline, 3);
	}

	public void act() {
		count++;
		if (x < 100) {
			dx = (int) (Math.random() * 2 + 1);
		} else if (x > Setting.PANEL_WIDTH - 100) {
			dx = -1 * (int) (Math.random() * 2 + 1);
		}
		x += dx;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	@Override
	public void shot(List<Bullet> bullets) {
		if (count == 20) {
			attack = (int) (Math.random() * 10);
			count = 0;
		}
		if (attack == 9) {
			try {
				bullets.add(new Bullet(x, y, -1, 2, 2));
				bullets.add(new Bullet(x, y, 0, 2, 2));
				bullets.add(new Bullet(x, y, 1, 2, 2));
			} catch (Exception e) {
				e.printStackTrace();
			}
			attack = 0;
		}
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
