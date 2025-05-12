package gameObject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import abstract_interface.Shottable;

public class StarShip implements Shottable {

	public int x, y, health;
	public static final int WIDTH = 20, HEIGHT = 20;
	private Image image;
	private int d = 1;
	public StarShip(int x, int y) {
		health = 10;
		this.x = x;
		this.y = y;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/starship.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawShape(Graphics g) {
		d = d + 1;
		Graphics2D g2d = (Graphics2D) g;
		if (image != null) {
			if (d == 1) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 2) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 3) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 4) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 5) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship5.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 6) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship4.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 7) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship3.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
			}
			if (d == 7) {
				try {
					image = ImageIO.read(getClass().getResourceAsStream("/starship2.png"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				g2d.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
				d = 1;
			}
		} else {
			g2d.setStroke(new BasicStroke(4));
			g2d.setColor(Color.GRAY);
			int[] x_outline = { x - WIDTH / 2, x, x + WIDTH / 2 };
			int[] y_outline = { y + HEIGHT / 2, y - HEIGHT / 2, y + HEIGHT / 2 };
			g2d.fillPolygon(x_outline, y_outline, 3);
			g2d.setColor(Color.blue);
			g2d.drawPolygon(x_outline, y_outline, 3);
		}
	}

	@Override
	public void shot(List<Bullet> bullets) {
		bullets.add(new Bullet(x - 6, y, 0, -10, 1));
		bullets.add(new Bullet(x + 6, y, 0, -10, 1));
	}

	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
	}

	public void gotDamaged() {
		health--;
	}

	public boolean alive() {
		return health > 0;
	}

}
