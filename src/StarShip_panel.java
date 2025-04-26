import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StarShip_panel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
	private int shipX = 400;
	private int shipY = 500;
	private Timer timer, hold_timer;
	private ArrayList<Bullet> bullets = new ArrayList<>();

	public StarShip_panel() {
		setFocusable(true);
		addMouseListener(this); // 註冊滑鼠事件
		addMouseMotionListener(this); // 註冊滑鼠移動事件
		timer = new Timer(20, this); // 每20ms更新一次
		timer.start();
		hold_timer = new Timer(80, e -> spawnBullet());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.BLACK);

		// 畫飛船
		int[] shipOutLineX = new int[3], shipOutLineY = new int[3];
		for (int i = 0; i < 3; i++) {
			shipOutLineX[i] = (shipX - 10) + 10 * i;
			if (i == 0 || i == 2) {
				shipOutLineY[i] = shipY + 9;
			} else {
				shipOutLineY[i] = shipY - 9;
			}
		}
		g.setColor(Color.CYAN);
		g.fillPolygon(shipOutLineX, shipOutLineY, 3);

		// 畫子彈
		g.setColor(Color.YELLOW);
		for (Bullet bullet : bullets) {
			g.fillRect(bullet.x, bullet.y, 5, 10);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 子彈移動
		for (Bullet bullet : bullets) {
			bullet.y -= 10;
		}
		// 刪掉超出畫面的子彈
		bullets.removeIf(bullet -> bullet.y < 0);

		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// 滑鼠按住發射子彈
		hold_timer.start();
	}

		//產生子彈
	public void spawnBullet() {
		bullets.add(new Bullet(shipX, shipY)); 
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// 滑鼠移動控制飛船
		shipX = e.getX();
		shipY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		hold_timer.stop();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// 滑鼠按住移動控制飛船
		shipX = e.getX();
		shipY = e.getY();
	}

	class Bullet {
		int x, y;

		Bullet(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
