package panelRelated;

import javax.swing.JFrame;

/**
 * 主程式入口，負責建立遊戲視窗並顯示起始畫面。
 */
public class SpaceInvaderDriver {

    /**
     * 程式進入點，建立 JFrame 並設定內容為起始畫面面板。
     * 
     * @param args 執行參數（此程式不使用）
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("A game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        StartScreenPanel startScreen = new StartScreenPanel(frame);
        frame.setContentPane(startScreen);
        frame.pack();
        frame.setVisible(true);
    }
}
