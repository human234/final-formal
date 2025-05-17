package gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 爆炸動畫類別，處理爆炸動畫的影格載入、更新與繪製。
 */
public class Explosion {

    /** 單一爆炸影格原始尺寸（寬與高） */
    private static final int FRAME_SIZE = 512;

    /** 縮放後的爆炸影格尺寸（寬與高） */
    private static final int SCALED_SIZE = 128;

    /** 精靈表的欄數 */
    private static final int SPRITE_COLS = 8;

    /** 精靈表的列數 */
    private static final int SPRITE_ROWS = 8;

    /** 總影格數 */
    private static final int TOTAL_FRAMES = SPRITE_COLS * SPRITE_ROWS;

    /** 爆炸動畫中心點 X 座標 */
    private int x;

    /** 爆炸動畫中心點 Y 座標 */
    private int y;

    /** 當前動畫播放的影格索引 */
    private int currentFrame = 0;

    /** 動畫是否已播放完畢 */
    private boolean isFinished = false;

    /** 爆炸動畫所有縮放後的影格陣列 */
    private static BufferedImage[] explosionFrames = null;

    /**
     * 建構子，建立爆炸動畫物件，並初始化位置。
     * 若爆炸影格尚未載入，會呼叫載入方法。
     *
     * @param x 爆炸動畫中心點 X 座標
     * @param y 爆炸動畫中心點 Y 座標
     */
    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;

        if (explosionFrames == null) {
            loadFrames();
        }
    }

    /**
     * 將圖片縮放到目標寬高大小。
     *
     * @param originalImage 原始圖片
     * @param targetWidth 目標寬度
     * @param targetHeight 目標高度
     * @return 縮放後的 BufferedImage
     */
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

    /**
     * 載入爆炸動畫的所有影格圖片，
     * 並將影格縮放成指定大小後存入陣列。
     */
    public static void loadFrames() {
        try {
            BufferedImage spriteSheet = ImageIO.read(Explosion.class.getResource("/explosion3.png"));
            explosionFrames = new BufferedImage[TOTAL_FRAMES];
            for (int i = 0; i < TOTAL_FRAMES; i++) {
                int col = i % SPRITE_COLS;
                int row = i / SPRITE_COLS;
                BufferedImage frame = spriteSheet.getSubimage(
                    col * FRAME_SIZE, row * FRAME_SIZE, FRAME_SIZE, FRAME_SIZE);
                explosionFrames[i] = resizeImage(frame, SCALED_SIZE, SCALED_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 執行動畫更新與繪製。
     *
     * @param g Graphics 物件，用於繪製爆炸動畫
     */
    public void drawShape(Graphics g) {
        update();
        render(g);
    }

    /**
     * 更新動畫狀態，移動到下一影格，並判斷動畫是否結束。
     */
    public void update() {
        currentFrame++;
        if (currentFrame >= TOTAL_FRAMES) {
            isFinished = true;
        }
    }

    /**
     * 繪製目前影格的爆炸動畫圖像。
     *
     * @param g Graphics 物件
     */
    public void render(Graphics g) {
        if (isFinished || explosionFrames == null || currentFrame >= TOTAL_FRAMES) return;

        int drawX = x - SCALED_SIZE / 2;
        int drawY = y - SCALED_SIZE / 2;
        g.drawImage(explosionFrames[currentFrame], drawX, drawY, null);
    }

    /**
     * 判斷動畫是否已結束，方便外部移除該爆炸物件。
     *
     * @return 若動畫已結束，回傳 true，否則回傳 false
     */
    public boolean shouldRemove() {
        return isFinished;
    }
}
