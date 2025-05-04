import javax.swing.JPanel;
import javax.swing.Timer;
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

public class SpaceInvader_panel extends JPanel
		implements ActionListener, MouseMotionListener, MouseListener {
	// 設定遊戲面板大小
	private static final int PANEL_WIDTH = 800;
	private static final int PANEL_HEIGHT = 600;

	private BufferedImage myImage;
	private Graphics myBuffer;
	private StarShip starShip; // 飛船
	private List<Bullet> bullets; // 子彈集合
	private List<EnemyBullet> enemyBullets;// 敵人子彈集合
	private Iterator<Bullet> bulletIterator;
	private Iterator<EnemyBullet> enemyBulletsIterator;
	private List<Enemy> enemies; // 敵人集合
	private Iterator<Enemy> enemyIterator;
	private Timer timer, timer_hold; // 遊戲計時器，用於更新畫面
	private int score; // 得分
	public SpaceInvader_panel() {
		
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
		setFocusable(true);
		addMouseListener(this);
		addMouseMotionListener(this);
		initSetting();
		myImage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		// 基本物件
		starShip = new StarShip(PANEL_WIDTH / 2, PANEL_HEIGHT - 30); // 起始位置設定
		enemyBullets = new ArrayList<>();
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
		// 初始化敵人，排列在上方
		int startX = 20;
		int startY = 50;
		int numEnemies = 8;
		int spacing = 40;
		for (int i = 0; i < numEnemies; i++) {
			enemies.add(new Enemy(startX + i * spacing, startY));
		}
		score = 0;
		// 設置計時器，每15毫秒執行一次動作 (大約66 FPS)
		timer = new Timer(10, this);
		timer_hold = new Timer(80, e -> spawnBullet());
		timer.start();
		
	}

	public void initSetting() {
		Setting.panelWidth = PANEL_WIDTH;
		Setting.panelHeight = PANEL_HEIGHT;
	}
	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
	}

	public void mouseDragged(MouseEvent e) {
		starShip.setX(e.getX());
		starShip.setY(e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		starShip.setX(e.getX());
		starShip.setY(e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		spawnBullet();
		timer_hold.start();
	}

	public void spawnBullet() {
		bullets.add(new Bullet(starShip.getX(), starShip.getY()));
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
		for (Enemy enemy : enemies) {
	        if (Math.random() < 0.01) { // 每一幀有 1% 機率發射
	        	enemyBullets.add(new EnemyBullet(enemy.getX(), enemy.getY())); 
	        }
	    }

	    // 移動敵人子彈
	    Iterator<EnemyBullet> enemyBulletIterator = enemyBullets.iterator();
	    while (enemyBulletIterator.hasNext()) {
	        EnemyBullet bullet = enemyBulletIterator.next();
	        bullet.move();
	        if (bullet.isOutOfScreen()) {
	            enemyBulletIterator.remove();
	        }
	    }
		posRefresh(); // 更新敵人位置
		collision(); // 碰撞檢測
		drawImage(); // 畫圖
		repaint();

	}

	public void posRefresh() {
		// 更新子彈位置，並移除超出螢幕外的子彈
		bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			bullet.move();
			if (bullet.isOutOfScreen()) {
				bulletIterator.remove();
			}
		}
		// 更新敵人位置
		for (Enemy enemy : enemies) {			
	        enemy.randomMove();
	        }
			
			
        }   	


	public void collision() {
		// 檢查子彈與敵人碰撞
		bulletIterator = bullets.iterator();
		while (bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			enemyIterator = enemies.iterator();
			while (enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();
				if (bullet.getBounds().intersects(enemy.getBounds())) {
					bulletIterator.remove();
					enemyIterator.remove();
					score += 10;
					break;
				}
			}
		}
	}

	public void drawImage() {
		myBuffer.setColor(Color.black);
		myBuffer.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
		// 繪製得分
		myBuffer.setColor(Color.WHITE);
		myBuffer.setFont(new Font("Arial", Font.BOLD, 20));
		myBuffer.drawString("Score: " + score, 10, 20);

		for (Bullet b : bullets) {
			b.drawShape(myBuffer);
		}
		for (Enemy en : enemies) {
			en.drawShape(myBuffer);
		}
		for (EnemyBullet  eb : enemyBullets) {
		    eb.drawShape(myBuffer); // 畫敵人子彈
		}
		starShip.drawShape(myBuffer);
	}
}