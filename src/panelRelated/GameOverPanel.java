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

		try {
			gameOverImage = ImageIO.read(getClass().getResource("/game_over.png"));
		} catch (IOException | IllegalArgumentException e) {
			System.out.println("null");
			gameOverImage = null;
		}

		timer = new Timer(3000, this);
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(gameOverImage, (int) (getWidth() * 0.33), (int) (getHeight() * 0.33), (int) (getWidth() * 0.33),
				(int) (getHeight() * 0.33), null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		frame.setContentPane(new StartScreenPanel(frame));
		frame.revalidate();
	}
}