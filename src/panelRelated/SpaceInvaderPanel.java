package panelRelated;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import abstract_interface.Enemy;
import abstract_interface.Shottable;
import gameObject.*;

import java.awt.Graphics;
import java.awt.Rectangle;
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

public class SpaceInvaderPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

	private JFrame frame;
	private BufferedImage myImage;
	private Graphics myBuffer;
	private StarShip starShip;
	private List<Bullet> bullets, bulletsEne;
	private Iterator<Bullet> bulletIterator;
	private List<Enemy> enemies;
	private Iterator<Enemy> enemyIterator;
	private List<Explosion> explosions;
	private Timer timer, timer_hold, timer_enemy;
	private int score, count;
	private ParallaxBackground parallax;
	
	public SpaceInvaderPanel(JFrame frame) {

		this.frame = frame;
		setPreferredSize(new Dimension(Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT));
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		myImage = new BufferedImage(Setting.PANEL_WIDTH, Setting.PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		Explosion.loadFrames();

		starShip = new StarShip(Setting.PANEL_WIDTH / 2, Setting.PANEL_HEIGHT - 30);
		bullets = new ArrayList<Bullet>();
		bulletsEne = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		explosions = new ArrayList<Explosion>();

		score = 0;

		timer = new Timer(16, this);
		timer.start();
		timer_enemy = new Timer(200, e -> spawnEnemy());
		timer_enemy.start();
		count = 0;
		timer_hold = new Timer(60, e -> starShip.shot(bullets));
	}

	public void spawnEnemy() {
		count++;
		if (count == 15 || count == 20) {
			enemies.add(new Triangle());
		}
		if (count <= 5) {
			enemies.add(new Square());
		}
		if (count % 10 == 0) {
			enemies.add(new Round());
		}
		if (count == 20) {
			count = 0;
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
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
			if (bullet.y < 0) {
				bulletIterator.remove();
			}
		}
		bulletIterator = bulletsEne.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.move();
			if (bullet.y > Setting.PANEL_HEIGHT || bullet.x > Setting.PANEL_WIDTH || bullet.x < -1 * Bullet.WIDTH) {
				bulletIterator.remove();
			}
		}
		enemyIterator = enemies.iterator();
		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();
			enemy.act();
			if (enemy instanceof Shottable) {
				((Shottable) enemy).shot(bulletsEne);
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
					addExplosion(enemy.getBounds().intersection(bullet.getBounds()));
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
		while (enemyIterator.hasNext()) {
			Enemy enemy = enemyIterator.next();
			if (enemy.getBounds().intersects(starShip.getBounds())) {
				addExplosion(enemy.getBounds().intersection(starShip.getBounds()));
				starShip.gotDamaged();
				enemyIterator.remove();
			}
		}

		bulletIterator = bulletsEne.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			if (bullet.getBounds().intersects(starShip.getBounds())) {
				addExplosion(bullet.getBounds().intersection(starShip.getBounds()));
				bulletIterator.remove();
				starShip.gotDamaged();
			}
		}

		explosions.removeIf(Explosion::shouldRemove);

		if (!starShip.alive()) {
			endGame();
		}

	}

	public void drawImage() {
		parallax.drawShape(myBuffer);
		myBuffer.setColor(Color.DARK_GRAY);
		myBuffer.fillRect(0, 0, getWidth(), getHeight());
		myBuffer.setColor(Color.WHITE);
		myBuffer.setFont(new Font("Arial", Font.BOLD, 20));
		myBuffer.drawString("Score: " + score, 10, 20);
		starShip.drawShape(myBuffer);
		
		for (Bullet bullet : bulletsEne) {
			bullet.drawShape(myBuffer);
		}
		for (Bullet bullet : bullets) {
			bullet.drawShape(myBuffer);
		}
		for (Enemy enemy : enemies) {
			enemy.drawShape(myBuffer);
		}
		for (Explosion explosion : explosions) {
			explosion.render(myBuffer);
			explosion.update();
		}

	}
	
	public void endGame() {
		bullets.clear();
		bulletsEne.clear();
		enemies.clear();

		timer.stop();
		timer_hold.stop();
		timer_enemy.stop();

		frame.setContentPane(new GameOverPanel(frame));
		frame.revalidate();
	}

	public void addExplosion(Rectangle intersection) {
		explosions
				.add(new Explosion(intersection.x + intersection.width / 2, intersection.y + intersection.height / 2));
	}
	public void load() {
		String[] bkPaths = { "/parallax-space-backgound.png", "/parallax-space-stars.png" };
		float[] bkSpeeds = { 2.2f, 4.3f };
		parallax = new ParallaxBackground(bkPaths, bkSpeeds);
		parallax.addObject("/parallax-space-big-planet.png", 2.1f, 3.2f, 200, 180);
		parallax.addObject("/parallax-space-ring-planet.png", 3.2f, 2.4f, 120, 240);
		parallax.addObject("/parallax-space-far-planets.png", 5.3f, 2.6f, 300, 140);
		Round.loadFrams();
		Square.loadFrams();
		Triangle.loadFrams();
	}
}
