package gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import abstract_interface.Shotter;
import panelRelated.Setting;

/**
 * 代號round的敵人類別，
 * 繼承自 Shotter，具有射擊能力且會自主移動與動畫切換。
 */
public class Round extends Shotter {
    /** X 軸移動速度 */
    private int dx;

    /** Y 軸移動速度 */
    private int dy;

    /** 敵人寬度 */
    public static final int WIDTH = 50;

    /** 敵人高度 */
    public static final int HEIGHT = 50;

    /** 改變方向計數器 */
    private int chDirCount;

    /** 射擊計數器 */
    private int shotCount;

    /** 方向改變的間隔時間 */
    private int chDirInterval;

    /** 是否為第一次移動 */
    private boolean firstStep;

    /** 影格圖片陣列 */
    private static Image[] imageFrames;

    /** 當前動畫影格 */
    private int currentFrame = 0;

    /** 影格延遲計數 */
    private int frameDelayCount = 0;

    /** 影格切換延遲常數 */
    private final int FRAME_DELAY = 32;

    /** 旋轉角度 */
    private double angle = 0;

    /**
     * 建構子，初始化 Round 敵人狀態與位置。
     * 初始位置依隨機分配在畫面寬度的三分之一處。
     */
    public Round() {
        firstStep = true;
        health = 5;
        double reg = Math.random();
        if (reg < 0.33) {
            x = (int) (Setting.PANEL_WIDTH / 4);
        } else if (reg < 0.66) {
            x = (int) (Setting.PANEL_WIDTH / 4 * 2);
        } else {
            x = (int) (Setting.PANEL_WIDTH / 4 * 3);
        }
        y = -1 * HEIGHT / 2;
        dx = 0;
        dy = 2;
        chDirInterval = 100 + (int) (Math.random() * 100);
        chDirCount = 0;
        shotCount = 0;
    }

    /**
     * 載入並切割動畫用的圖片資源。
     * 從資源路徑讀取圖片並分割成多個影格。
     */
    public static void loadFrams() {
        try {
            BufferedImage spriteSheet = ImageIO.read(Round.class.getResource("/Ninja.png"));
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
     * 敵人的行為邏輯，
     * 包含方向變換、邊界碰撞反彈與位置更新。
     */
    @Override
    public void act() {
        shotCount++;
        chDirCount++;
        if (chDirCount >= chDirInterval) {
            double r = Math.random();
            if (r <= 0.33) {
                dx = (int) (Math.random() * -3);
            } else if (r <= 0.66) {
                dx = 0;
            } else {
                dx = (int) (Math.random() * 3);
            }
            r = Math.random();
            if (r <= 0.33) {
                dy = (int) (Math.random() * -3);
            } else if (r <= 0.66) {
                dy = 0;
            } else {
                dy = (int) (Math.random() * 3);
            }
            chDirCount = 0;
            if (dx == 0 && dy == 0) {
                chDirCount += 150;
            }
        }
        if (y > 60) {
            firstStep = false;
        }
        if (!firstStep) {
            if (x + WIDTH / 2 >= Setting.PANEL_WIDTH || x - WIDTH / 2 <= 0) {
                dx = -dx;
            }
            if (y + HEIGHT / 2 >= Setting.PANEL_HEIGHT || y - HEIGHT / 2 <= 0) {
                dy = -dy;
            }
        }
        x += dx;
        y += dy;
    }

    /**
     * 取得敵人的碰撞範圍矩形。
     * 用於碰撞偵測。
     * 
     * @return 碰撞範圍 Rectangle 物件
     */
    public Rectangle getBounds() {
        return new Rectangle(x - WIDTH / 2, y - HEIGHT / 2, WIDTH, HEIGHT);
    }

    /**
     * 繪製敵人圖形，包含動畫更新與渲染。
     * 
     * @param g Graphics 物件
     */
    public void drawShape(Graphics g) {
        render(g);
        update();
    }

    /**
     * 更新動畫影格與旋轉角度。
     */
    public void update() {
        frameDelayCount++;
        if (frameDelayCount > FRAME_DELAY) {
            if (currentFrame < 3) {
                currentFrame++;
            } else {
                currentFrame = 0;
            }
            frameDelayCount = 0;
        }
        angle += Math.toRadians(5);
    }

    /**
     * 使用 Graphics2D 進行旋轉並繪製當前影格。
     * 
     * @param g Graphics 物件
     */
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform old = g2d.getTransform();

        g2d.translate(x, y);
        g2d.rotate(angle);
        g2d.drawImage(imageFrames[currentFrame], -WIDTH / 2, -HEIGHT / 2, null);
        g2d.setTransform(old);
    }

    /**
     * 執行射擊行為。
     * 依據當前移動方向產生子彈，並加入全局子彈列表。
     */
    @Override
    public void shot() {
        if (shotCount == 40) {
            int xDir, yDir;
            if (dx > 0) {
                xDir = 10;
            } else if (dx == 0) {
                xDir = 0;
            } else {
                xDir = -10;
            }
            if (dy > 0) {
                yDir = 10;
            } else if (dy == 0) {
                yDir = 0;
            } else {
                yDir = -10;
            }
            if (xDir != 0 || yDir != 0) {
                try {
                    bullets.add(new Bullet(x, y, xDir, yDir, 5, 2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            shotCount = 0;
        }
    }

    /**
     * 使敵人受到傷害，減少血量。
     * 
     * @param damage 傷害值
     */
    @Override
    public void gotDamaged(int damage) {
        health -= damage;
    }

    /**
     * 取得敵人目前的血量。
     * 
     * @return 血量值
     */
    @Override
    public int getHealth() {
        return health;
    }

    /**
     * 判斷敵人是否還存活。
     * 
     * @return 血量大於 0 則回傳 true，否則回傳 false
     */
    @Override
    public boolean alive() {
        return health > 0;
    }
}
