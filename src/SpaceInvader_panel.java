import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SpaceInvader_panel extends JPanel implements KeyListener, ActionListener {
	// 設定遊戲面板大小
	private static final int PANEL_WIDTH = 800;
	private static final int PANEL_HEIGHT = 600;

	private BufferedImage myImage;
	private Graphics myBuffer;
	private StarShip starShip; // 飛船
	private List<Bullet> bullets; // 子彈集合
	private Iterator<Bullet> bulletIterator;
	private List<Enemy> enemies; // 敵人集合
	private Iterator<Enemy> enemyIterator;
	private Timer timer; // 遊戲計時器，用於更新畫面
	private int score; // 得分

	public SpaceInvader_panel() {
		setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(this);
		myImage = new BufferedImage(PANEL_WIDTH, PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		// 基本物件
		starShip = new StarShip(PANEL_WIDTH / 2, PANEL_HEIGHT - 30); //起始位置設定
		bullets = new ArrayList<Bullet>();
		enemies = new ArrayList<Enemy>();
		// 初始化敵人，排列在上方
		int startX = 100;
		int startY = 50;
		int numEnemies = 8;
		int spacing = 80;
		for (int i = 0; i < numEnemies; i++) {
			enemies.add(new Enemy(startX + i * spacing, startY, PANEL_WIDTH));
		}
		score = 0;
		// 設置計時器，每15毫秒執行一次動作 (大約66 FPS)
		timer = new Timer(15, this);
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, null);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		switch (key) {
		case KeyEvent.VK_LEFT:
			if(starShip.getX() > 0) {
				starShip.moveLeft();
			}
			break;
		case KeyEvent.VK_RIGHT:
			if(starShip.getX() < PANEL_WIDTH) {
				starShip.moveRight();
			}
			break;
		case KeyEvent.VK_UP:
			if(starShip.getY() > 0) {
				starShip.moveUp();
			}
			break;
		case KeyEvent.VK_DOWN:
			if(starShip.getY() < PANEL_HEIGHT) {
				starShip.moveDown();
			}
			break;
		default:
			break;
		}
		// 發射子彈
		if(key == KeyEvent.VK_SPACE) {
			System.out.println("i see you see");
			int bulletX = starShip.getX();
			int bulletY = starShip.getY();
			bullets.add(new Bullet(bulletX, bulletY));
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		posRefresh(); //更新敵人位置
		collision();  //碰撞檢測
		drawImage();  //畫圖
		repaint();
		
	}
	
	public void posRefresh() {
		// 更新子彈位置，並移除超出螢幕外的子彈
		bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()){
			Bullet bullet = bulletIterator.next();
			bullet.move();
			if(bullet.isOutOfScreen()) {
				bulletIterator.remove();
			}
		}
		// 更新敵人位置
		for (Enemy enemy : enemies) {
			enemy.move();
		}
	}
	
	public void collision() {
		// 檢查子彈與敵人碰撞
		bulletIterator = bullets.iterator();
		while(bulletIterator.hasNext()) {
			Bullet bullet = bulletIterator.next();
			enemyIterator = enemies.iterator();
			while(enemyIterator.hasNext()) {
				Enemy enemy = enemyIterator.next();
				if(bullet.getBounds().intersects(enemy.getBounds())) {
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
		starShip.drawShape(myBuffer);
	}
}

