package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import abstract_interface.Shottable;

public class StarShip implements Shottable {

	public int x, y, health;
	public static final int WIDTH = 20, HEIGHT = 20;
	private static Image[] image;
	public static List<Bullet> bullets;
	private int currentFrame = 0;
	private double angle = 0;
	private boolean rotateLeft = false, rotateRight = false;

	public StarShip(int x, int y) {
		health = 100;
		this.x = x;
		this.y = y;
	}

	public static void loadImaages() {
		image = new Image[8];
		try {
			for (int i = 1; i <= 5; i++) {
				BufferedImage origin = ImageIO.read(StarShip.class.getResource("/starship" + i + ".png"));
				image[i - 1] = origin.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			}
			for (int i = 4; i >= 2; i--) {
				BufferedImage origin = ImageIO.read(StarShip.class.getResource("/starship" + i + ".png"));
				image[9 - i] = origin.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void drawShape(Graphics g) {
		update();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform old = g2d.getTransform();

		g2d.translate(x, y);
		g2d.rotate(angle);

		g.drawImage(image[currentFrame], -image[currentFrame].getWidth(null) / 2,
				-image[currentFrame].getHeight(null) / 2, null);

		g2d.setTransform(old);
	}

	@Override
	public void shot() {
		double speed = 10;

		double dx = Math.sin(angle) * speed;
		double dy = -Math.cos(angle) * speed;

		int offset = 10;

		double leftX = x + Math.cos(angle) * -offset;
		double leftY = y + Math.sin(angle) * -offset;
		double rightX = x + Math.cos(angle) * offset;
		double rightY = y + Math.sin(angle) * offset;

		bullets.add(new Bullet((int) leftX, (int) leftY, (int) dx, (int) dy, 1, 1));
		bullets.add(new Bullet(x, y, (int) dx, (int) dy, 1, 1));
		bullets.add(new Bullet((int) rightX, (int) rightY, (int) dx, (int) dy, 1, 1));
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public void gotDamaged(int damage) {
		health -= damage;
	}

	public boolean alive() {
		return health > 0;
	}

	public void setRotateLeft(boolean state) {
		rotateLeft = state;
	}

	public void setRotateRight(boolean state) {
		rotateRight = state;
	}

	public void update() {
		if (currentFrame < 6) {
			currentFrame++;
		} else {
			currentFrame = 0;
		}
		if (rotateLeft) {
			angle -= Math.toRadians(2);
		} else if (rotateRight) {
			angle += Math.toRadians(2);
		}
	}

}
