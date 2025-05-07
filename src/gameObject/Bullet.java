package gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bullet {
	public int x, y, dx, dy;
	public static final int WIDTH = 5;
	public static final int HEIGHT = 10;
	private Image image;

	public Bullet(int x, int y, int dx, int dy, int type) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		if (type == 1) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/bullet.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (type == 2) {
			try {
				image = ImageIO.read(getClass().getResourceAsStream("/enemybullet.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			image = null;
		}

	}

	public void move() {
		x += dx;
		y += dy;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public void drawShape(Graphics g) {
		if (image != null) {
			g.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
		} else {
			g.setColor(Color.yellow);
			g.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
		}
	}
}
