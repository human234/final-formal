import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class StarShip {
	private int x, y; // 飛船的位置
	private static final int WIDTH = 20; // 飛船寬度
	private static final int HEIGHT = 20; // 飛船高度
	private static final int MOVE_SPEED = 10; // 飛船移動速度

	public StarShip(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/** 向左移動飛船 */
	public void moveLeft() {
		x -= MOVE_SPEED;
	}

	/** 向右移動飛船 */
	public void moveRight() {
		x += MOVE_SPEED;
	}

	/** 向上移動飛船 */
	public void moveUp() {
		y -= MOVE_SPEED;
	}

	/** 向下移動飛船 */
	public void moveDown() {
		y += MOVE_SPEED;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return WIDTH;
	}

	public int getHeight() {
		return HEIGHT;
	}

	// 繪製飛船
	public void drawShape(Graphics g) {
		g.setColor(Color.GRAY);
		int[] x_outline = { getX() - WIDTH / 2, getX(), getX() + WIDTH / 2};
		int[] y_outline = { getY() + HEIGHT / 2, getY() - HEIGHT / 2, getY() + HEIGHT / 2};
		g.fillPolygon(x_outline, y_outline, 3);
	}
}
