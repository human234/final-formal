package panelRelated;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpaceInvader_driver {
	public static void main(String[] args) {
		JFrame frame = new JFrame("A game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		StartScreenPanel startScreen = new StartScreenPanel(frame);
		frame.setContentPane(startScreen);

		frame.pack();
		frame.setVisible(true);
	}
}

