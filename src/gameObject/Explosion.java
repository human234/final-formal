package gameObject;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Explosion {

    private static final int FRAME_SIZE = 512;  // ?üÂ?Â≠êÂ??ÑÂ§ßÂ∞?
    private static final int SCALED_SIZE = 128;  // Á∏ÆÊîæÂæåÁ?Â≠êÂ?Â§ßÂ?
    private static final int SPRITE_COLS = 8;
    private static final int SPRITE_ROWS = 8;
    private static final int TOTAL_FRAMES = SPRITE_COLS * SPRITE_ROWS;

    private int x, y;              // √z¨µ§§§ﬂ¶Ï∏m
    private int currentFrame = 0;  // ∑Ì´eºΩ©Ò™∫¥V
    private boolean isFinished = false;

    private static BufferedImage[] explosionFrames = null;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;

        if (explosionFrames == null) {
            loadFrames();
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return resizedImage;
    }

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

    public void update() {
        currentFrame++;
        if (currentFrame >= TOTAL_FRAMES) {
            isFinished = true;
        }
    }

    public void render(Graphics g) {
        if (isFinished || explosionFrames == null || currentFrame >= TOTAL_FRAMES) return;

        int drawX = x - SCALED_SIZE / 2;
        int drawY = y - SCALED_SIZE / 2;
        g.drawImage(explosionFrames[currentFrame], drawX, drawY, null);
    }

    public boolean shouldRemove() {
        return isFinished;
    }
}