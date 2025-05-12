package abstract_interface;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Enemy{
	
	protected int health;
	public int x, y;

	public abstract void drawShape(Graphics g);

	public abstract Rectangle getBounds();

	public abstract void gotDamaged(int damage);
	
	public abstract int getHealth();
	
	public abstract boolean alive();

	public abstract void act();
	

}
