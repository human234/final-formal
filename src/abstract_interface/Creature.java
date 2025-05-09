package abstract_interface;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Creature {

	protected int health;
	public int x, y;

	public abstract void drawShape(Graphics g);

	public abstract Rectangle getBounds();

	public abstract void gotDamaged();

	public abstract boolean alive();

}
