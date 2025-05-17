package abstract_interface;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 抽象類別 Enemy 表示遊戲中的敵人單位。
 * 所有敵人需實作基本行為，例如繪圖、取得邊界、受傷反應等。
 */
public abstract class Enemy {
    
    /** 敵人的血量 */
    protected int health;

    /** 敵人的 X 軸座標 */
    public int x;

    /** 敵人的 Y 軸座標 */
    public int y;

    /**
     * 繪製敵人的圖形。
     * 
     * @param g 圖形物件，用於繪圖
     */
    public abstract void drawShape(Graphics g);

    /**
     * 取得敵人的碰撞範圍（矩形）。
     * 
     * @return 表示敵人邊界的 Rectangle 物件
     */
    public abstract Rectangle getBounds();

    /**
     * 讓敵人受到傷害。
     * 
     * @param damage 傷害值
     */
    public abstract void gotDamaged(int damage);

    /**
     * 取得敵人目前的血量。
     * 
     * @return 血量數值
     */
    public abstract int getHealth();

    /**
     * 檢查敵人是否還存活。
     * 
     * @return 若存活則傳回 true，否則傳回 false
     */
    public abstract boolean alive();

    /**
     * 敵人的行動邏輯，例如移動、攻擊等。
     */
    public abstract void act();
}
