package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import abstract_interface.Shottable;

/**
 * 玩家控制的星際飛船類別，實作了可發射子彈的介面 Shottable。
 * 飛船可左右旋轉並有簡單的動畫。
 */
public class StarShip implements Shottable {

    /** 飛船的 X 座標 */
    public int x;
    /** 飛船的 Y 座標 */
    public int y;
    /** 飛船的生命值 */
    public int health;

    /** 飛船碰撞箱寬度 */
    public static final int WIDTH = 20;
    /** 飛船碰撞箱高度 */
    public static final int HEIGHT = 20;

    /** 飛船動畫圖片陣列 */
    private static Image[] image;
    /** 子彈清單 (應該由外部賦值) */
    public static List<Bullet> bullets;

    /** 當前動畫幀數 */
    private int currentFrame = 0;
    /** 飛船旋轉角度（弧度制） */
    private double angle = 0;

    /** 是否正在向左旋轉 */
    private boolean rotateLeft = false;
    /** 是否正在向右旋轉 */
    private boolean rotateRight = false;

    /**
     * 建構子，設定飛船初始座標及生命值。
     * @param x 初始 X 座標
     * @param y 初始 Y 座標
     */
    public StarShip(int x, int y) {
        health = 100;
        this.x = x;
        this.y = y;
    }

    /**
     * 載入飛船動畫圖片資源。
     * 共載入8張圖片，前5張依序，後3張倒序以達循環效果。
     */
    public static void loadImaages() {
        image = new Image[8];
        try {
            for (int i = 1; i <= 5; i++) {
                BufferedImage origin = ImageIO.read(StarShip.class.getResource("/starship" + i + ".png"));
                image[i - 1] = origin.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            }
            for (int i = 4; i >= 2; i--) {
                BufferedImage origin = ImageIO.read(StarShip.class.getResource("/starship" + i + ".png"));
                image[9 - i] = origin.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 繪製飛船，包含旋轉效果與動畫幀切換。
     * @param g Graphics 繪圖物件
     */
    public void drawShape(Graphics g) {
        update();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);

        g.drawImage(image[currentFrame], -image[currentFrame].getWidth(null) / 2,
                -image[currentFrame].getHeight(null) / 2, null);

        g2d.setTransform(old);
    }

    /**
     * 發射子彈，從飛船左右兩側及正前方同時發射3發子彈。
     */
    @Override
    public void shot() {
        double speed = 10;

        double dx = Math.sin(angle) * speed;
        double dy = -Math.cos(angle) * speed;

        int offset = 10;

        double leftX = x + Math.cos(angle) * -offset;
        double leftY = y + Math.sin(angle) * -offset;
        double rightX = x + Math.cos(angle) * offset;
        double rightY = y + Math.sin(angle) * offset;

        bullets.add(new Bullet((int) leftX, (int) leftY, (int) dx, (int) dy, 1, 1));
        bullets.add(new Bullet(x, y, (int) dx, (int) dy, 1, 1));
        bullets.add(new Bullet((int) rightX, (int) rightY, (int) dx, (int) dy, 1, 1));
    }

    /**
     * 取得飛船碰撞範圍。
     * @return 碰撞矩形區域
     */
    public Rectangle getBounds() {
        return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    /**
     * 飛船受到傷害時減少生命值。
     * @param damage 傷害值
     */
    public void gotDamaged(int damage) {
        health -= damage;
    }

    /**
     * 判斷飛船是否仍存活。
     * @return true 生命值大於0，false 否則
     */
    public boolean alive() {
        return health > 0;
    }

    /**
     * 設定是否向左旋轉。
     * @param state true 表示開始向左旋轉，false 停止
     */
    public void setRotateLeft(boolean state) {
        rotateLeft = state;
    }

    /**
     * 設定是否向右旋轉。
     * @param state true 表示開始向右旋轉，false 停止
     */
    public void setRotateRight(boolean state) {
        rotateRight = state;
    }

    /**
     * 更新動畫狀態與旋轉角度。
     */
    public void update() {
        if (currentFrame < 6) {
            currentFrame++;
        } else {
            currentFrame = 0;
        }
        if (rotateLeft) {
            angle -= Math.toRadians(2);
        } else if (rotateRight) {
            angle += Math.toRadians(2);
        }
    }
}
