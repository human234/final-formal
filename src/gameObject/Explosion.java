package gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Explosion {
    private static final int FRAME_SIZE = 256;  // 每個子圖的大小
    private static final int SPRITE_COLS = 8;
    private static final int SPRITE_ROWS = 8;
    private static final int TOTAL_FRAMES = SPRITE_COLS * SPRITE_ROWS;

    private int x, y;              // 爆炸中心位置
    private int currentFrame = 0;  // 當前播放的幀
    private boolean isFinished = false;

    private static BufferedImage[] explosionFrames = null;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;

        if (explosionFrames == null) {
            loadFrames();
        }
    }

    public static void loadFrames() {
        try {
            BufferedImage spriteSheet = ImageIO.read(Explosion.class.getResource("/explosion2.png"));
            explosionFrames = new BufferedImage[TOTAL_FRAMES];
            for (int i = 0; i < TOTAL_FRAMES; i++) {
                int col = i % SPRITE_COLS;
                int row = i / SPRITE_COLS;
                explosionFrames[i] = spriteSheet.getSubimage(
                    col * FRAME_SIZE, row * FRAME_SIZE, FRAME_SIZE, FRAME_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        currentFrame++;
        if (currentFrame >= TOTAL_FRAMES) {
            isFinished = true;
        }
    }

    public void render(Graphics g) {
        if (isFinished || explosionFrames == null || currentFrame >= TOTAL_FRAMES) return;

        int drawX = x - FRAME_SIZE / 2;
        int drawY = y - FRAME_SIZE / 2;
        g.drawImage(explosionFrames[currentFrame], drawX, drawY, null);
    }

    public boolean shouldRemove() {
        return isFinished;
    }
}