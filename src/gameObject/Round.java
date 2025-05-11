package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import abstract_interface.Enemy;
import abstract_interface.Shottable;
import panelRelated.Setting;

public class Round extends Enemy implements Shottable {
	private int dx, dy;
	public static final int WIDTH = 40;
	public static final int HEIGHT = 40;
	private int chDirCount, shotCount, chDirInterval;
	private boolean firstStep;
	private static Image[] imageFrames;
	
	public static void loadFrams() {
		try {
			BufferedImage spriteSheet = ImageIO.read(Round.class.getResource("/Ninja.png"));
			imageFrames = new Image[4];
			for (int i = 0; i < 4; i++) {
				BufferedImage sub = spriteSheet.getSubimage(32 * i, 0, 32, 32);
				imageFrames[i] = sub.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Round() {
		firstStep = true;
		health = 5;
		double reg = Math.random();
		if (reg < 0.33) {
			x = (int) (Setting.PANEL_WIDTH / 4);
		} else if (reg < 0.66) {
			x = (int) (Setting.PANEL_WIDTH / 4 * 2);
		} else {
			x = (int) (Setting.PANEL_WIDTH / 4 * 3);
		}
		y = -1 * HEIGHT / 2;
		dx = 0;
		dy = 2;
		chDirInterval = 100 + (int) (Math.random() * 100);
		chDirCount = 0;
		shotCount = 0;
	}

	@Override
	public void act() {
		shotCount++;
		chDirCount++;
		if (chDirCount >= chDirInterval) {
			double r = Math.random();
			if (r <= 0.33) {
				dx = (int) (Math.random() * -3);
			} else if (r <= 0.66) {
				dx = 0;
			} else {
				dx = (int) (Math.random() * 3);
			}
			r = Math.random();
			if (r <= 0.33) {
				dy = (int) (Math.random() * -3);
			} else if (r <= 0.66) {
				dy = 0;
			} else {
				dy = (int) (Math.random() * 3);
			}
			chDirCount = 0;
			if (dx == 0 && dy == 0) {
				chDirCount += 150;
			}
		}
		if (y > 60) {
			firstStep = false;
		}
		if (!firstStep) {
			if (x + WIDTH / 2 >= Setting.PANEL_WIDTH || x - WIDTH / 2 <= 0) {
				dx = -dx;
			}
			if (y + HEIGHT / 2 >= Setting.PANEL_HEIGHT || y - HEIGHT / 2 <= 0) {
				dy = -dy;
			}
		}
		x += dx;
		y += dy;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(Color.RED);
		g2d.fillOval(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
		g2d.setColor(Color.GRAY);
		g2d.drawOval(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	@Override
	public void shot(List<Bullet> bullets) {
		if (shotCount == 40) {
			int xDir, yDir;
			if (dx > 0) {
				xDir = 10;
			} else if (dx == 0) {
				xDir = 0;
			} else {
				xDir = -10;
			}
			if (dy > 0) {
				yDir = 10;
			} else if (dy == 0) {
				yDir = 0;
			} else {
				yDir = -10;
			}
			if (xDir != 0 || yDir != 0) {
				try {
					bullets.add(new Bullet(x, y, xDir, yDir, 2));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			shotCount = 0;
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