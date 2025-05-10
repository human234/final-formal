package panelRelated;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ParallaxObject {
	private BufferedImage image;
	private float x;
	private int y;
	private float speed;
	private float scale;
	private int screenWidth;
	private int delayCounter = 0;
	private int delayFrames;

	private boolean visible = true;

	public ParallaxObject(BufferedImage image, float speed, float scale, int y, int screenWidth, int delayFrames) {
		this.image = image;
		this.speed = speed;
		this.scale = scale;
		this.y = y;
		this.screenWidth = screenWidth;
		this.delayFrames = delayFrames;
		this.x = screenWidth;
	}

	public void drawShape(Graphics g) {
		render(g);
		update();
	}

	public void update() {
		if (!visible) {
			delayCounter++;
			if (delayCounter >= delayFrames) {
				x = screenWidth;
				visible = true;
				delayCounter = 0;
			}
			return;
		}

		x -= speed;

		if (x + getScaledWidth() < 0) {
			visible = false;
		}
	}

	public void render(Graphics g) {
		if (!visible)
			return;

		int drawW = getScaledWidth();
		int drawH = getScaledHeight();
		g.drawImage(image, (int) x, y, drawW, drawH, null);
	}

	private int getScaledWidth() {
		return (int) (image.getWidth() * scale);
	}

	private int getScaledHeight() {
		return (int) (image.getHeight() * scale);
	}
}
