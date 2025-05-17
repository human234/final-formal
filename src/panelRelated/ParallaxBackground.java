package panelRelated;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 實現視差背景效果的類別，包含多層背景圖片和平行移動的小物件。
 */
public class ParallaxBackground {
    /** 背景圖片陣列 */
    private BufferedImage[] backgrounds;
    /** 各背景層的移動速度 */
    private float[] speeds;
    /** 各背景層的當前偏移量 */
    private float[] offsets = { 0, 0 };
    /** 平行移動的小物件列表 */
    private ArrayList<ParallaxObject> objects = new ArrayList<>();

    /**
     * 建構子，初始化背景圖片與速度。
     * @param imagePaths 背景圖片路徑陣列，必須至少有兩張圖片
     * @param speeds 對應背景的水平移動速度陣列
     */
    public ParallaxBackground(String[] imagePaths, float[] speeds) {
        this.speeds = speeds;
        backgrounds = new BufferedImage[imagePaths.length];
        try {
            for (int i = 0; i < 2; i++) {
                backgrounds[i] = ImageIO.read(getClass().getResourceAsStream(imagePaths[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 新增一個平行移動的小物件。
     * @param imagePath 小物件的圖片路徑
     * @param speed 小物件移動速度
     * @param scale 縮放比例
     * @param y 小物件垂直位置（y座標）
     * @param delayFrames 小物件消失後重新出現的延遲幀數
     */
    public void addObject(String imagePath, float speed, float scale, int y, int delayFrames) {
        try {
            BufferedImage objImage = ImageIO.read(getClass().getResourceAsStream(imagePath));
            ParallaxObject obj = new ParallaxObject(objImage, speed, scale, y, Setting.PANEL_WIDTH, delayFrames);
            objects.add(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 繪製背景與小物件，並更新狀態。
     * @param g 繪圖物件
     */
    public void drawShape(Graphics g) {
        render(g);
        update();
    }

    /**
     * 更新背景偏移量與小物件狀態。
     */
    public void update() {
        // 背景偏移更新
        for (int i = 0; i < 2; i++) {
            offsets[i] += speeds[i];
            if (offsets[i] > Setting.PANEL_WIDTH)
                offsets[i] -= Setting.PANEL_WIDTH;
            if (offsets[i] < 0)
                offsets[i] += Setting.PANEL_WIDTH;
        }
        // 更新所有小物件
        for (ParallaxObject obj : objects) {
            obj.update();
        }
    }

    /**
     * 繪製背景與所有小物件，背景會重複繪製以實現無縫平移效果。
     * @param g 繪圖物件
     */
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);

        for (int i = 0; i < 2; i++) {
            int x = (int) -offsets[i];
            // 繪製兩次背景以達成無縫循環
            g2d.drawImage(backgrounds[i], x, 0, Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT, null);
            g2d.drawImage(backgrounds[i], x + Setting.PANEL_WIDTH, 0, Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT, null);
        }

        // 繪製所有小物件
        for (ParallaxObject obj : objects) {
            obj.render(g);
        }
    }
}
