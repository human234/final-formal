package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import abstract_interface.Shotter;
import panelRelated.Setting;

public class Triangle extends Shotter {

	private int dx, count;
	private int attack;

	public static final int WIDTH = 40, HEIGHT = 40;
	private static Image[] imageFrames;
	private int currentFrame = 0, frameDelayCount = 0;
	private final int FRAME_DELAY = 32;

	public Triangle() {
		health = 5;
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

	public static void loadFrams() {
		try {
			BufferedImage spriteSheet = ImageIO.read(Round.class.getResource("/Ligher.png"));
			imageFrames = new Image[4];
			for (int i = 0; i < 4; i++) {
				BufferedImage sub = spriteSheet.getSubimage(32 * i, 0, 32, 32);
				imageFrames[i] = sub.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void drawShape(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (imageFrames != null) {
			render(g);
			update();
		} else {
			g2d.setStroke(new BasicStroke(4));
			g2d.setColor(Color.GRAY);
			int[] x_outline = { x - WIDTH / 2, x, x + WIDTH / 2 };
			int[] y_outline = { y - HEIGHT / 2, y + HEIGHT / 2, y - HEIGHT / 2 };
			g2d.fillPolygon(x_outline, y_outline, 3);
			g2d.setColor(Color.red);
			g2d.drawPolygon(x_outline, y_outline, 3);
		}
	}

	public void update() {
		if (frameDelayCount < FRAME_DELAY) {
			frameDelayCount++;
		} else {
			if (currentFrame < 3) {
				currentFrame++;
			} else {
				currentFrame = 0;
			}
			frameDelayCount = 0;
		}
	}

	public void render(Graphics g) {
		g.drawImage(imageFrames[currentFrame], x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT, null);
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
	public void shot() {
		if (count == 10) {
			attack = (int) (Math.random() * 10);
			count = 0;
		}
		if (attack == 9) {
			try {
				bullets.add(new Bullet(x, y, -1, 5, 3, 2));
				bullets.add(new Bullet(x, y, 0, 5, 3, 2));
				bullets.add(new Bullet(x, y, 1, 5, 3, 2));
			} catch (Exception e) {
				e.printStackTrace();
			}
			attack = 0;
		}
	}

	@Override
	public void gotDamaged(int damage) {
		health -= damage;
	}

	@Override
	public int getHealth() {
		return health;
	}

	@Override
	public boolean alive() {
		return health > 0;
	}

}
