package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class StarShip implements Shottable {
	
	private int health = 10;
	public int x, y;
	public static final int WIDTH = 20, HEIGHT = 20; // 飛船寬度、高度

	public StarShip(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// 繪製飛船
	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.GRAY);
		int[] x_outline = { x - WIDTH / 2, x, x + WIDTH / 2 };
		int[] y_outline = { y + HEIGHT / 2, y - HEIGHT / 2, y + HEIGHT / 2 };
		g2d.fillPolygon(x_outline, y_outline, 3);
		g2d.setColor(Color.blue);
		g2d.drawPolygon(x_outline, y_outline, 3);
	}

	@Override
	public void shot(List<Bullet> bullets) {
		bullets.add(new Bullet(x, y, -10, Color.yellow));
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT /2, WIDTH, HEIGHT);
	}
	
	public void gotDamaged() {
		health--;
	}
	
	public boolean alive() {
		return health > 0;
	}

}
