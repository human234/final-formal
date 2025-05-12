package panelRelated;

import javax.swing.*;

import gameObject.SoundPlayer;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class GameOverPanel extends JPanel implements ActionListener, MouseListener {

	private BufferedImage gameOverImage;
	private Timer timer;
	private JFrame frame;
	private boolean hasSwitched = false;
	private SoundPlayer bgm; 
	
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

//		timer = new Timer(3000, this);
//		timer.setRepeats(false);
//		timer.start();
		addMouseListener(this);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.drawImage(gameOverImage, (int) (getWidth() * 0.33), (int) (getHeight() * 0.33), (int) (getWidth() * 0.33),
				(int) (getHeight() * 0.33), null);
	}

	private void switchToStartScreen() {
		if (!hasSwitched) {
			hasSwitched = true;
			bgm.stop();
			frame.setContentPane(new StartScreenPanel(frame));
			frame.revalidate();
			
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		switchToStartScreen();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		switchToStartScreen();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		switchToStartScreen();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		switchToStartScreen();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}