package gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Explosion {
    private BufferedImage[] frames;
    private int x, y;
    private int currentFrame;
    private int frameDelay = 5; // 控制播放速度，數值越大越慢
    private int delayCounter = 0;
    private boolean finished;

    public Explosion(int x, int y, BufferedImage[] frames) {
        this.x = x;
        this.y = y;
        this.frames = frames;
        this.currentFrame = 0;
        this.finished = false;
    }

    public void update() {
        delayCounter++;
        if (delayCounter >= frameDelay) {
            delayCounter = 0;
            currentFrame++;
            if (currentFrame >= frames.length) {
                finished = true;
            }
        }
    }

    public void draw(Graphics g) {
        if (!finished && currentFrame < frames.length) {
            g.drawImage(frames[currentFrame], x, y, null);
        }
    }

    public boolean isFinished() {
        return finished;
    }
}
