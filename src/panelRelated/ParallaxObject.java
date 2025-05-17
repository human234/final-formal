package panelRelated;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 視差背景中的平行移動小物件，會以固定速度從右向左移動並可設定出現延遲。
 */
public class ParallaxObject {
    /** 小物件圖片 */
    private BufferedImage image;
    /** 小物件當前水平位置 */
    private float x;
    /** 小物件垂直位置 */
    private int y;
    /** 水平移動速度 */
    private float speed;
    /** 縮放比例 */
    private float scale;
    /** 螢幕寬度，用於重置小物件位置 */
    private int screenWidth;
    /** 延遲計數器 */
    private int delayCounter = 0;
    /** 延遲幀數 */
    private int delayFrames;
    /** 小物件是否可見 */
    private boolean visible = true;

    /**
     * 建構子，初始化小物件的屬性。
     * @param image 小物件圖片
     * @param speed 移動速度
     * @param scale 縮放比例
     * @param y 垂直位置
     * @param screenWidth 螢幕寬度
     * @param delayFrames 消失後重新出現的延遲幀數
     */
    public ParallaxObject(BufferedImage image, float speed, float scale, int y, int screenWidth, int delayFrames) {
        this.image = image;
        this.speed = speed;
        this.scale = scale;
        this.y = y;
        this.screenWidth = screenWidth;
        this.delayFrames = delayFrames;
        this.x = screenWidth;
    }

    /**
     * 繪製小物件並更新狀態。
     * @param g 繪圖物件
     */
    public void drawShape(Graphics g) {
        render(g);
        update();
    }

    /**
     * 更新小物件位置，並處理消失及重新出現的延遲邏輯。
     */
    public void update() {
        if (!visible) {
            delayCounter++;
            if (delayCounter >= delayFrames) {
                x = screenWidth;
                visible = true;
                delayCounter = 0;
            }
            return;
        }

        x -= speed;

        if (x + getScaledWidth() < 0) {
            visible = false;
        }
    }

    /**
     * 繪製小物件影像，若不可見則不繪製。
     * @param g 繪圖物件
     */
    public void render(Graphics g) {
        if (!visible)
            return;

        int drawW = getScaledWidth();
        int drawH = getScaledHeight();
        g.drawImage(image, (int) x, y, drawW, drawH, null);
    }

    /** 取得縮放後的寬度 */
    private int getScaledWidth() {
        return (int) (image.getWidth() * scale);
    }

    /** 取得縮放後的高度 */
    private int getScaledHeight() {
        return (int) (image.getHeight() * scale);
    }
}
