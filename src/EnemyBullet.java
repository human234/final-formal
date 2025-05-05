import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

public class EnemyBullet {
    private int x, y; // 子彈的位置
    private static final int WIDTH = 5; // 子彈寬度
    private static final int HEIGHT = 10; // 子彈高度
    private static final int SPEED = 3; // 子彈移動速度
    private BufferedImage image; //子彈圖片
	
	public EnemyBullet(int x, int y) {
		this.x = x;
		this.y = y;

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/enemybullet.png")); // 或用 getResourceAsStream 載入資源
		} catch (IOException e) {
			System.err.println("載入圖片失敗: " + e.getMessage());
		}
	}

    public void move() {
        y += SPEED;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /** 取得子彈的邊界矩形，用於碰撞檢測 */
    public Rectangle getBounds() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    /** 子彈是否已經飛出螢幕 */
    public boolean isOutOfScreen() {
        return y > 600;
    }
    
    // 繪製子彈 
    public void drawShape(Graphics g) {
		if (image != null) {
			g.drawImage(image, x - image.getWidth() / 2, y - image.getHeight() / 2, null);
		} else {
			// 若圖片載入失敗則顯示黃色矩形
			g.setColor(Color.yellow);
	        g.fillRect(getX(), getY(), WIDTH, HEIGHT);
		}
	}
}