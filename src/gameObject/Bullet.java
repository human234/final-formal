package gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bullet {
	public int x, y, dx, dy, damage;
	public static final int WIDTH = 5;
	public static final int HEIGHT = 10;
	private static Image image1, image2;
	private int imageType;

	public Bullet(int x, int y, int dx, int dy, int damage, int type) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.damage = damage;
		this.imageType = type;
		SoundPlayer player = new SoundPlayer("/shoot.wav");
		player.playOnce();
	}

	public static void loadImage() {
		try {
			image1 = ImageIO.read(Bullet.class.getResourceAsStream("/bullet.png"));
			image2 = ImageIO.read(Bullet.class.getResourceAsStream("/enemybullet.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void act() {
		x += dx;
		y += dy;
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public void drawShape(Graphics g) {
		if (imageType == 1) {
			g.drawImage(image1, x - image1.getWidth(null) / 2, y - image1.getHeight(null) / 2, null);
		} else if(imageType == 2) {
			g.drawImage(image1, x - image2.getWidth(null) / 2, y - image2.getHeight(null) / 2, null);
		}
	}
}
