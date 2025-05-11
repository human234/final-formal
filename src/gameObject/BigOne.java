package gameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import abstract_interface.Shotter;
import panelRelated.Setting;

public class BigOne extends Shotter  {
	private int dx = 5, shotDelayCount = 0;
	private final int SHOT_DELAY = 20;
	public static final int WIDTH = 60, HEIGHT = 60;

	public BigOne() {
		this.x = Setting.PANEL_WIDTH / 2;
		this.y = 80;
		this.health = 100;
	}

	@Override
	public void drawShape(Graphics g) {
		g.setColor(Color.MAGENTA);
		g.fillOval(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
		g.setColor(Color.WHITE);
		displayHealth(g);

	}

	private void displayHealth(Graphics g) {
		// HP Bar 背景
		int barWidth = WIDTH;
		int barHeight = 8;
		int barX = x - barWidth / 2;
		int barY = y - HEIGHT / 2 - 15;

		g.setColor(Color.DARK_GRAY);
		g.fillRect(barX, barY, barWidth, barHeight);

		// HP Bar 前景（根據血量變化長度）
		float healthRatio = Math.max(0, Math.min(1, health / 100.0f)); // 限定 0~1 範圍
		int currentBarWidth = (int) (barWidth * healthRatio);

		if (healthRatio > 0.6) {
			g.setColor(Color.GREEN);
		} else if (healthRatio > 0.3) {
			g.setColor(Color.ORANGE);
		} else {
			g.setColor(Color.RED);
		}
		g.fillRect(barX, barY, currentBarWidth, barHeight);

		// 邊框
		g.setColor(Color.BLACK);
		g.drawRect(barX, barY, barWidth, barHeight);
	}


	public void act() {
		x += dx;
		if (x < 100 || x > Setting.PANEL_WIDTH - 100) {
			dx = -dx;
		}
	}

	@Override
	public void shot() {
		shotDelayCount++;
		if(shotDelayCount == SHOT_DELAY) {
			bullets.add(new Bullet(x, y, -1, 4, 2)); 
			bullets.add(new Bullet(x, y, 0, 4, 2)); 
			bullets.add(new Bullet(x, y, 1, 4, 2));
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
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
