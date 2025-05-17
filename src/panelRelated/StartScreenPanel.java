package panelRelated;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gameObject.SoundPlayer;

/**
 * 遊戲啟動畫面面板類別。
 * 顯示遊戲開始背景圖與開始按鈕，並播放背景音樂。
 * 使用者點擊開始按鈕後，切換至遊戲主畫面 (SpaceInvaderPanel)。
 */
public class StartScreenPanel extends JPanel {
    
    private JFrame frame;
    
    /**
     * 建構子，初始化啟動畫面並設定背景音樂與開始按鈕。
     * @param frame 傳入 JFrame 物件，用於切換面板。
     */
    public StartScreenPanel(JFrame frame) {
        this.frame = frame;
        SoundPlayer bgm = new SoundPlayer("/startmusic.wav");
        bgm.playOnceThenRepeat();

        setPreferredSize(new Dimension(Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT));
        setLayout(new BorderLayout());

        // 背景圖片設定
        ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/startBackground.jpg"));
        JLabel backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(null);

        // 開始按鈕設定與圖片縮放
        ImageIcon buttonIcon = new ImageIcon(getClass().getResource("/button.png"));
        Image scaledButtonImage = buttonIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon newButtonIcon = new ImageIcon(scaledButtonImage);
        JButton button = new JButton(newButtonIcon);

        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBounds(70, Setting.PANEL_HEIGHT - 400, 200, 200);

        // 按鈕點擊事件：切換面板並停止背景音樂
        button.addActionListener(e -> switchPanel());
        button.addActionListener(e -> bgm.stop());

        backgroundLabel.add(button);

        add(backgroundLabel, BorderLayout.CENTER);
    }
    
    /**
     * 切換到遊戲主畫面 (SpaceInvaderPanel)。
     */
    public void switchPanel() {
        frame.setContentPane(new SpaceInvaderPanel(frame));
        frame.revalidate();
    }
}
