import javax.swing.JFrame;

public class StarShip {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Star Wars Mini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new StarShip_panel());
        frame.setVisible(true);
    }
}
