import javax.swing.*;
import java.awt.*;
import java.awt.Panel;

public class StarShip_panel extends JPanel {
	public void paintComponent(Graphics g) {
		g.setColor(Color.yellow);
		g.fillOval(150, 150, 300, 300);
	}
}
