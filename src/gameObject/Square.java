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

import abstract_interface.Enemy;
import panelRelated.Setting;

public class Square extends Enemy {

	private int dx;
	private double dy, dy_culmu;
	public static final int WIDTH = 30, HEIGHT = 30;
	private boolean dir;
	private static Image[] imageFrames;
	
	public static void loadFrams() {
		try {
			BufferedImage spriteSheet = ImageIO.read(Round.class.getResource("/Paranoid.png"));
			imageFrames = new Image[4];
			for (int i = 0; i < 4; i++) {
				BufferedImage sub = spriteSheet.getSubimage(32 * i, 0, 32, 32);
				imageFrames[i] = sub.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_SMOOTH);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
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
