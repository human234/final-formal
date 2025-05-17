package panelRelated;

import javax.swing.*;

import gameObject.SoundPlayer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
 * 遊戲結束畫面面板，顯示遊戲結束圖片並播放結束音樂。
 * 使用者點擊畫面後會切換回開始畫面。
 */
public class GameOverPanel extends JPanel implements ActionListener, MouseListener {

    /** 遊戲結束圖片 */
    private BufferedImage gameOverImage;
    /** 主要 JFrame 容器 */
    private JFrame frame;
    /** 防止多次切換的旗標 */
    private boolean hasSwitched = false;
    /** 背景音樂播放器 */
    private SoundPlayer bgm;

    /**
     * 建構子，初始化畫面尺寸、載入圖片及播放結束音樂，並加入滑鼠監聽器。
     * @param frame 傳入 JFrame 物件，用於切換面板
     */
    public GameOverPanel(JFrame frame) {
        this.frame = frame;
        bgm = new SoundPlayer("/ending.wav");
        bgm.playOnce();
        setPreferredSize(new Dimension(Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT));

        try {
            gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("null");
            gameOverImage = null;
        }

//      timer = new Timer(3000, this);
//      timer.setRepeats(false);
//      timer.start();
        addMouseListener(this);
    }

    /**
     * 繪製面板內容，背景為黑色並繪製遊戲結束圖片。
     * @param g 繪圖物件
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(gameOverImage, (int) (getWidth() * 0.33), (int) (getHeight() * 0.33), (int) (getWidth() * 0.33),
                (int) (getHeight() * 0.33), null);
    }

    /**
     * 切換回開始畫面，避免重複切換。
     * 停止背景音樂並將 JFrame 內容面板換成 StartScreenPanel。
     */
    private void switchToStartScreen() {
        if (!hasSwitched) {
            hasSwitched = true;
            bgm.stop();
            frame.setContentPane(new StartScreenPanel(frame));
            frame.revalidate();
        }
    }

    /**
     * 計時器事件觸發時呼叫（目前未啟用）。
     * @param e 事件物件
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switchToStartScreen();
    }

    /**
     * 使用者點擊滑鼠時切換畫面。
     * @param e 滑鼠事件物件
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        switchToStartScreen();
    }

    /**
     * 使用者按下滑鼠時切換畫面。
     * @param e 滑鼠事件物件
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switchToStartScreen();
    }

    /**
     * 使用者放開滑鼠時切換畫面。
     * @param e 滑鼠事件物件
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        switchToStartScreen();
    }

    /**
     * 使用者滑鼠進入元件範圍時觸發（未實作）。
     * @param e 滑鼠事件物件
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // 未實作
    }

    /**
     * 使用者滑鼠離開元件範圍時觸發（未實作）。
     * @param e 滑鼠事件物件
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // 未實作
    }
}
