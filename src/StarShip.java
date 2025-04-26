import javax.swing.JFrame;

public class StarShip {

	public static void main(String[] args) {
		JFrame frame = new JFrame("start_ship");
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 600);
		frame.setVisible(true);
		frame.setContentPane(new StarShip_panel());
	}

}
