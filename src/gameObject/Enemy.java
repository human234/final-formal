package gameObject;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy {

	protected int health;
	public int x, y;

	public abstract void drawShape(Graphics g);

	public abstract void move();

	public abstract Rectangle getBounds();

	public abstract void gotDamaged();

	public abstract boolean alive();
}
