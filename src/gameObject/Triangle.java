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
import abstract_interface.Shotter;
import panelRelated.Setting;

/**
 * 代號三角形敵人的類別，繼承自 Shotter。
 * 擁有簡單的左右移動行為與動畫，並能隨機發射子彈。
 */
public class Triangle extends Shotter {

    /** 水平移動速度 */
    private int dx;
    /** 計數器，用於控制攻擊間隔 */
    private int count;
    /** 攻擊狀態標記 */
    private int attack;

    /** 三角形寬度 */
    public static final int WIDTH = 40;
    /** 三角形高度 */
    public static final int HEIGHT = 40;

    /** 動畫圖片陣列 */
    private static Image[] imageFrames;
    /** 當前動畫幀數 */
    private int currentFrame = 0;
    /** 幀延遲計數 */
    private int frameDelayCount = 0;
    /** 每幀延遲時間（用於動畫更新速度控制） */
    private final int FRAME_DELAY = 32;

    /**
     * 建構子，初始化三角形敵人的生命值、初始位置與移動狀態。
     * 水平位置隨機分布於畫面左半或右半區域，垂直位置三段式隨機。
     */
    public Triangle() {
        health = 5;
        count = 0;
        x = -1 * WIDTH / 2 + Setting.PANEL_WIDTH * (int) (Math.random() * 2);
        double reg = Math.random();
        if (reg < 0.33) {
            y = 30;
        } else if (reg < 0.66) {
            y = 70;
        } else {
            y = 110;
        }
    }

    /**
     * 載入動畫圖片資源。
     * 從精靈圖中擷取4張影格，並縮放成指定大小。
     */
    public static void loadFrames() {
        try {
            BufferedImage spriteSheet = ImageIO.read(Triangle.class.getResource("/Ligher.png"));
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
     * 繪製三角形敵人，若有動畫圖則顯示動畫，否則畫三角形替代。
     * @param g 繪圖物件
     */
    @Override
    public void drawShape(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (imageFrames != null) {
            render(g);
            update();
        } else {
            g2d.setStroke(new BasicStroke(4));
            g2d.setColor(Color.GRAY);
            int[] x_outline = { x - WIDTH / 2, x, x + WIDTH / 2 };
            int[] y_outline = { y - HEIGHT / 2, y + HEIGHT / 2, y - HEIGHT / 2 };
            g2d.fillPolygon(x_outline, y_outline, 3);
            g2d.setColor(Color.red);
            g2d.drawPolygon(x_outline, y_outline, 3);
        }
    }

    /**
     * 更新動畫狀態，根據幀延遲控制動畫切換速度。
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
     * 根據當前動畫幀繪製三角形敵人圖片。
     * @param g 繪圖物件
     */
    public void render(Graphics g) {
        g.drawImage(imageFrames[currentFrame], x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT, null);
    }

    /**
     * 執行敵人行為，包含左右隨機移動。
     * 控制移動方向並避免移出畫面邊界。
     */
    public void act() {
        count++;
        if (x < 100) {
            dx = (int) (Math.random() * 2 + 1);
        } else if (x > Setting.PANEL_WIDTH - 100) {
            dx = -1 * (int) (Math.random() * 2 + 1);
        }
        x += dx;
    }

    /**
     * 取得敵人的碰撞矩形範圍，用於碰撞偵測。
     * @return 碰撞矩形區域
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    /**
     * 敵人攻擊行為，根據計數器和隨機機率發射子彈。
     * 當攻擊條件達成時，發射三顆子彈，分別帶有不同的水平速度。
     */
    @Override
    public void shot() {
        if (count == 10) {
            attack = (int) (Math.random() * 10);
            count = 0;
        }
        if (attack == 9) {
            try {
                bullets.add(new Bullet(x, y, -1, 5, 3, 2));
                bullets.add(new Bullet(x, y, 0, 5, 3, 2));
                bullets.add(new Bullet(x, y, 1, 5, 3, 2));
            } catch (Exception e) {
                e.printStackTrace();
            }
            attack = 0;
        }
    }

    /**
     * 飽受傷害時減少生命值。
     * @param damage 傷害值
     */
    @Override
    public void gotDamaged(int damage) {
        health -= damage;
    }

    /**
     * 取得當前生命值。
     * @return 生命值數值
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * 判斷敵人是否還活著。
     * @return 若生命值大於0回傳 true，否則回傳 false
     */
    @Override
    public boolean alive() {
        return health > 0;
    }
}
