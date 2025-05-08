package panelRelated;

import javax.swing.JFrame;

public class SpaceInvaderDriver {
	public static void main(String[] args) {
		JFrame frame = new JFrame("A game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		StartScreenPanel startScreen = new StartScreenPanel(frame);
		frame.setContentPane(startScreen);

		frame.pack();
		frame.setVisible(true);
	}
}

