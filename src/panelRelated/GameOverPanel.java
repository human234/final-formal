package panelRelated;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class GameOverPanel extends JPanel implements ActionListener {

	private BufferedImage gameOverImage;
	private Timer timer;
	private JFrame frame;

	public GameOverPanel(JFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT));
		setFocusable(true);

		// 載入圖片
		try {
			gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("null");
			gameOverImage = null;
		}

		// 自動回主畫面倒數（例如 3 秒）
		timer = new Timer(3000, this);
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// 畫背景
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		// 畫 Game Over 圖
		int imgX = (getWidth() - gameOverImage.getWidth()) / 2;
		int imgY = (getHeight() - gameOverImage.getHeight()) / 2;
		g.drawImage(gameOverImage, imgX, imgY, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 回主畫面
		frame.setContentPane(new StartScreenPanel(frame));
		frame.revalidate();
	}
}