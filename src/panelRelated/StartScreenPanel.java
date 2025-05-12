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
public class StartScreenPanel extends JPanel {
	private JFrame frame;
	public StartScreenPanel(JFrame frame) {
		this.frame = frame;
		SoundPlayer bgm = new SoundPlayer("/sound/startmusic.wav");
		bgm.playOnceThenRepeat();
		setPreferredSize(new Dimension(Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT));
		setLayout(new BorderLayout());

		ImageIcon backgroundImage = new ImageIcon(getClass().getResource("/startBackground.jpg"));
		JLabel backgroundLabel = new JLabel(backgroundImage);
		backgroundLabel.setLayout(null);

		ImageIcon buttonIcon = new ImageIcon(getClass().getResource("/button.png"));
		Image scaledButtonImage = buttonIcon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon newButtonIcon = new ImageIcon(scaledButtonImage);
		JButton button = new JButton(newButtonIcon);
		button.setContentAreaFilled(false);
		button.setBorderPainted(false);
		button.setFocusPainted(false);
		button.setBounds(70, Setting.PANEL_HEIGHT - 400, 200, 200);
		button.addActionListener(e -> switchPanel());
		button.addActionListener(e -> bgm.stop());
		backgroundLabel.add(button);

		add(backgroundLabel, BorderLayout.CENTER);
	}
	
	public void switchPanel() {
		frame.setContentPane(new SpaceInvaderPanel(frame));
		frame.revalidate();
	}
}