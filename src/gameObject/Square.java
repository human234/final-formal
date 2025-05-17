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

/**
 * 代號square的敵人。
 * 具有移動、繪製與生命值管理功能，
 * 並可載入與顯示動畫影格。
 */
public class Square extends Enemy {

    /**
     * 水平移動速度。
     */
    private int dx;

    /**
     * 垂直移動的累積浮點值。
     */
    private double dy, dy_culmu;

    /**
     * 敵人寬度。
     */
    public static final int WIDTH = 50, HEIGHT = 50;

    /**
     * 水平移動方向，true 表示向右，false 表示向左。
     */
    private boolean dir;

    /**
     * 存放動畫影格的陣列。
     */
    private static Image[] imageFrames;

    /**
     * 當前動畫影格索引。
     */
    private int currentFrame = 0, frameDelayCount = 0;

    /**
     * 動畫影格切換延遲計數，數值越大動畫越慢。
     */
    private final int FRAME_DELAY = 32;

    /**
     * 建構子，初始化敵人位置、速度與方向。
     */
    public Square() {
        health = 12;
        x = -1 * WIDTH / 2 + Setting.PANEL_WIDTH * (int) (Math.random() * 2);
        y = 100;
        dx = 8;
        dir = true;
        if (x > Setting.PANEL_WIDTH) {
            dir = !dir;
            dx *= -1;
        }
        dy = 0.9;
    }

    /**
     * 載入動畫影格，從精靈圖切割並縮放。
     * 建議遊戲啟動時呼叫一次。
     */
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

    /**
     * 執行敵人行為邏輯，包含邊界判斷與移動。
     */
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

    /**
     * 取得敵人的碰撞矩形區域。
     * @return 敵人的邊界矩形
     */
    public Rectangle getBounds() {
        return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    /**
     * 繪製敵人外觀，若有動畫影格則繪製動畫，
     * 否則繪製紅色方塊。
     * @param g Graphics 物件
     */
    public void drawShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (imageFrames != null) {
            render(g);
            update();
        } else {
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.RED);
            g2d.fillRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
            g2d.setColor(Color.black);
            g2d.drawRect(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
        }
    }

    /**
     * 更新動畫影格計數器並切換到下一張影格。
     */
    public void update() {
        if (frameDelayCount < FRAME_DELAY) {
            frameDelayCount++;
        } else {
            if (currentFrame < 3) {
                currentFrame++;
            } else {
                currentFrame = 0;
            }
            frameDelayCount = 0;
        }
    }

    /**
     * 繪製當前動畫影格。
     * @param g Graphics 物件
     */
    public void render(Graphics g) {
        g.drawImage(imageFrames[currentFrame], x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT, null);
    }

    /**
     * 受到傷害時扣除生命值。
     * @param damage 傷害值
     */
    @Override
    public void gotDamaged(int damage) {
        health -= damage;
    }

    /**
     * 取得當前生命值。
     * @return 生命值
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * 判斷敵人是否還存活。
     * @return 如果生命值大於0則為true，否則false
     */
    @Override
    public boolean alive() {
        return health > 0;
    }

}
