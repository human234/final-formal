package panelRelated;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.IOException;
import java.util.ArrayList;

public class ParallaxBackground {
	private BufferedImage[] backgrounds;
	private float[] speeds;
	private float[] offsets = { 0, 0 };

	private ArrayList<ParallaxObject> objects = new ArrayList<>();

	public ParallaxBackground(String[] imagePaths, float[] speeds) {
		this.speeds = speeds;
		backgrounds = new BufferedImage[imagePaths.length];
		try {
			for (int i = 0; i < 2; i++) {
				backgrounds[i] = ImageIO.read(getClass().getResourceAsStream(imagePaths[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void addObject(String imagePath, float speed, float scale, int y, int delayFrames) {
		try {
			BufferedImage objImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
			ParallaxObject obj = new ParallaxObject(objImage, speed, scale, y, Setting.PANEL_WIDTH, delayFrames);
			objects.add(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void drawShape(Graphics g) {
		render(g);
		update();
	}

	public void update() {
		// 背景更新
		for (int i = 0; i < 2; i++) {
			offsets[i] += speeds[i];
			if (offsets[i] > Setting.PANEL_WIDTH)
				offsets[i] -= Setting.PANEL_WIDTH;
			if (offsets[i] < 0)
				offsets[i] += Setting.PANEL_WIDTH;
		}
		// 小物件更新
		for (ParallaxObject obj : objects) {
			obj.update();
		}
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

		for (int i = 0; i < 2; i++) {
			int x = (int) -offsets[i];
			// 背景繪製兩次以達成無縫平移
			g2d.drawImage(backgrounds[i], x, 0, Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT, null);
			g2d.drawImage(backgrounds[i], x + Setting.PANEL_WIDTH, 0, Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT, null);
		}

		// 小物件繪製
		for (ParallaxObject obj : objects) {
			obj.render(g);
		}
	}
}