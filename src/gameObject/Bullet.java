package gameObject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * 子彈類別，表示遊戲中發射出的子彈。
 * 包含位置、速度、傷害及繪製邏輯。
 */
public class Bullet {
    /** 子彈的 X 座標 */
    public int x;

    /** 子彈的 Y 座標 */
    public int y;

    /** 子彈的 X 軸速度 */
    public int dx;

    /** 子彈的 Y 軸速度 */
    public int dy;

    /** 子彈造成的傷害 */
    public int damage;

    /** 子彈寬度 */
    public static final int WIDTH = 5;

    /** 子彈高度 */
    public static final int HEIGHT = 10;

    /** 子彈圖片1（玩家子彈） */
    private static Image image1;

    /** 子彈圖片2（敵人子彈） */
    private static Image image2;

    /** 子彈類型，用以決定繪製哪張圖片 */
    private int imageType;

    /**
     * 建構子，建立一個子彈實例。
     * 同時播放射擊音效。
     * 
     * @param x 子彈初始 X 座標
     * @param y 子彈初始 Y 座標
     * @param dx 子彈在 X 軸的速度
     * @param dy 子彈在 Y 軸的速度
     * @param damage 子彈傷害值
     * @param type 子彈圖片類型（1: 玩家子彈，2: 敵人子彈）
     */
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

    /**
     * 載入子彈用的圖片資源。
     * 從資源路徑讀取玩家與敵人子彈的圖片。
     */
    public static void loadImage() {
        try {
            image1 = ImageIO.read(Bullet.class.getResourceAsStream("/bullet.png"));
            image2 = ImageIO.read(Bullet.class.getResourceAsStream("/enemybullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 子彈移動邏輯，根據速度更新位置。
     */
    public void act() {
        x += dx;
        y += dy;
    }

    /**
     * 取得子彈的碰撞範圍矩形，用於碰撞偵測。
     * 
     * @return 子彈的碰撞矩形範圍
     */
    public Rectangle getBounds() {
        return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    /**
     * 繪製子彈影像。
     * 根據 imageType 決定繪製玩家或敵人子彈圖片。
     * 
     * @param g Graphics 物件，用於繪圖
     */
    public void drawShape(Graphics g) {
        if (imageType == 1) {
            g.drawImage(image1, x - image1.getWidth(null) / 2, y - image1.getHeight(null) / 2, null);
        } else if(imageType == 2) {
            g.drawImage(image2, x - image2.getWidth(null) / 2, y - image2.getHeight(null) / 2, null);
        }
    }
}
