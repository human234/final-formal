import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StarShip_panel extends JPanel implements ActionListener,KeyListener {
	private int shipX = 400;
	private int shipY = 500;
	
	int SHIP_WIDTH = 20;
	int SHIP_HEIGHT = 20;
	// 子彈
    private List<Point> bullets = new ArrayList<Point>();

    // 敵人
    private List<Rectangle> enemies = new ArrayList<Rectangle>();
    private int enemySpeed = 2;           // 整體水平速度
    private static final int DROP_DIST = 10; // 每次下移量

    // 分數
    private int score = 0;
	
    private Timer timer, hold_timer;
	
	
//	private ArrayList<Bullet> bullets = new ArrayList<>();
	
	public StarShip_panel() {
		setFocusable(true);
		addKeyListener(this); 
		// 初始化敵人：4 行 8 列，每格 40×20，間隔 10 px
        int rows = 4, cols = 8;
        int w = 40, h = 20, paddingX = 10, paddingY = 10;
        int startX = 30, startY = 30;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * (w + paddingX);
                int y = startY + r * (h + paddingY);
                enemies.add(new Rectangle(x, y, w, h));
            }
        }
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
		for (Point b : bullets) {
			g.fillRect(b.x, b.y, 5, 10);
		}
		// 畫敵人
        g.setColor(Color.RED);
        for (Rectangle e : enemies) {
            g.fillRect(e.x, e.y, e.width, e.height);
        }

        // 畫分數
        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// 子彈移動
		for (Point b : bullets) {
			b.y -= 10;
		}
		// 刪掉超出畫面的子彈
		bullets.removeIf(bullet -> bullet.y < 0);
		// 2. 更新敵人位置：水平移動，碰邊就反向並下移
        boolean hitEdge = false;
        for (Rectangle enemy : enemies) {
            enemy.x += enemySpeed;
            if (enemy.x < 0 || enemy.x + enemy.width > getWidth()) {
                hitEdge = true;
            }
        }
        if (hitEdge) {
            enemySpeed = -enemySpeed;
            for (Rectangle enemy : enemies) {
                enemy.y += DROP_DIST;
            }
        }
     // 3. 碰撞檢測：子彈 vs 敵人
        Iterator<Rectangle> eit = enemies.iterator();
        while (eit.hasNext()) {
            Rectangle enemy = eit.next();
            boolean removed = false;
            Iterator<Point> bit2 = bullets.iterator();
            while (bit2.hasNext()) {
                Point b = bit2.next();
                Rectangle shot = new Rectangle(b.x, b.y, 4, 10);
                if (shot.intersects(enemy)) {
                    // 碰撞：移除敵人與子彈，+1 分
                    eit.remove();
                    bit2.remove();
                    score += 1;
                    removed = true;
                    break;
                }
            }
            if (removed) {
                // 如果這個敵人已被移除，不用再檢查它
                continue;
            }
        }

        repaint();
	}
	
	public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // 左右移動
        if (code == KeyEvent.VK_LEFT && shipX > 10) {
            shipX -= 10;
        } else if (code == KeyEvent.VK_RIGHT && shipX < getWidth() - SHIP_WIDTH) {
            shipX += 10;
        }else if (code == KeyEvent.VK_UP && shipY > 10) {
            shipY -= 10;
        } else if (code == KeyEvent.VK_DOWN && shipY < getHeight() - SHIP_HEIGHT) {
            shipY += 10;
        }
        // 空白鍵發射
        else if (code == KeyEvent.VK_SPACE) {
        	hold_timer.start();
        	if (code == KeyEvent.VK_LEFT && shipX > 10) {
                shipX -= 10;
            } else if (code == KeyEvent.VK_RIGHT && shipX < getWidth() - SHIP_WIDTH) {
                shipX += 10;
            }else if (code == KeyEvent.VK_UP && shipY > 10) {
                shipY -= 10;
            } else if (code == KeyEvent.VK_DOWN && shipY < getHeight() - SHIP_HEIGHT) {
                shipY += 10;
            }
        }
        repaint();
    }
	
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		if (code == KeyEvent.VK_SPACE) {
			hold_timer.stop();
        }
	}
	public void spawnBullet() {
		bullets.add(new Point(shipX, shipY)); 
	}
	


	class Bullet {
		int x, y;

		Bullet(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}	

}