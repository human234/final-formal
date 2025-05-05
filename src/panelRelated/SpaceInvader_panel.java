package panelRelated;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import gameObject.Bullet;
import gameObject.Enemy;
import gameObject.StarShip;
import gameObject.Triangle;
import gameObject.Square;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SpaceInvader_panel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	// è¨­å®šé�Šæˆ²é�¢æ�¿å¤§å°�
	private static final int PANEL_WIDTH = 800;
	private static final int PANEL_HEIGHT = 600;

	private JFrame frame;
	private BufferedImage myImage;
	private Graphics myBuffer;
	private StarShip starShip; // é£›èˆ¹
	private List<Bullet> bullets, bulletsEne; // å­�å½ˆé›†å�ˆ
	private Iterator<Bullet> bulletIterator;
	private List<Enemy> enemies; // æ•µäººé›†å�ˆ
	private Iterator<Enemy> enemyIterator;
	private Timer timer, timer_hold, timer_triangle, timer_square; // é�Šæˆ²è¨ˆæ™‚å™¨ï¼Œç”¨æ–¼æ›´æ–°ç•«é�¢
	private int score; // å¾—åˆ†

	public SpaceInvader_panel(JFrame frame) {

		this.frame = frame;
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		myImage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		// åŸºæœ¬ç‰©ä»¶
		starShip = new StarShip(PANEL_WIDTH / 2, PANEL_HEIGHT - 30); // èµ·å§‹ä½�ç½®è¨­å®š
		bullets = new ArrayList<Bullet>();
		bulletsEne = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();

		score = 0;

		timer = new Timer(10, this);
		timer.start();
		timer_hold = new Timer(60, e -> starShip.shot(bullets));
		timer_triangle = new Timer(2000, e -> spawntriangle());
		timer_triangle.start();
		timer_square = new Timer(400, e -> spawnSquare());
		timer_square.start();
	}

	public void spawntriangle() {
		for (int i = 0; i < 2; i++) {
			enemies.add(new Triangle());
		}
	}

	public void spawnSquare() {
		enemies.add(new Square());
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
	}

	public void mouseDragged(MouseEvent e) {
		starShip.x = e.getX();
		starShip.y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		starShip.x = e.getX();
		starShip.y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		starShip.shot(bullets);
		timer_hold.start();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		timer_hold.stop();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		posRefresh();
		collision();
		drawImage();
		repaint();

	}

	public void posRefresh() {
		bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.move();
			if (bullet.isOutOfScreen()) {
				bulletIterator.remove();
			}
		}
		bulletIterator = bulletsEne.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.move();
			if (bullet.isOutOfScreen()) {
				bulletIterator.remove();
			}
		}
		enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();
			enemy.move();
			if(enemy instanceof Triangle) {
				((Triangle) enemy).shot(bulletsEne);
			}
		}
	}

	public void collision() {
		bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			enemyIterator = enemies.iterator();
			while (enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();
				if (enemy.getBounds().intersects(bullet.getBounds())) {
					bulletIterator.remove();
					enemy.gotDamaged();
					if (!enemy.alive()) {
						enemyIterator.remove();
						score += 10;
					}
					break;
				}
			}
		}

		enemyIterator = enemies.iterator();
		while(enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();
			if(enemy instanceof Square) {
				if(enemy.getBounds().intersects(starShip.getBounds())) {
					starShip.gotDamaged();
					enemyIterator.remove();
				}
			}
		}
		
		bulletIterator = bulletsEne.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if (bullet.getBounds().intersects(starShip.getBounds())) {
				starShip.gotDamaged();
			}
		}
		
		if (!starShip.alive()) {
			endGame();
		}

	}

	public void drawImage() {
		myBuffer.setColor(Color.DARK_GRAY);
		myBuffer.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		// ç¹ªè£½å¾—åˆ†
		myBuffer.setColor(Color.WHITE);
		myBuffer.setFont(new Font("Arial", Font.BOLD, 20));
		myBuffer.drawString("Score: " + score, 10, 20);

		for (Bullet b : bulletsEne) {
			b.drawShape(myBuffer);
		}
		for (Bullet b : bullets) {
			b.drawShape(myBuffer);
		}
		for (Enemy en : enemies) {
			en.drawShape(myBuffer);
		}

		starShip.drawShape(myBuffer);
	}

	public void endGame() {
		bullets.clear();
		bulletsEne.clear();
		enemies.clear();

		timer.stop();
		timer_hold.stop();
		timer_triangle.stop();
		timer_square.stop();

		frame.setContentPane(new GameOverPanel(frame));
		frame.revalidate();
	}
}
