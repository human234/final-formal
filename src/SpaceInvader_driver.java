import javax.swing.JFrame;

public class SpaceInvader_driver {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Star Wars Mini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new SpaceInvader_panel());
        frame.pack();
        frame.setVisible(true);
    }
}
