package main;

import java.awt.Rectangle;

import main.ID;

public class LifeBarGegner extends GameObject {

	private Handler handler;
	private GameObject owner;
	private float fixX;
	private float fixY;
	private Rectangle rec = new Rectangle((int) x, (int) y, 100, 3);
	public int live = 3;
	private HUD hud;

	public LifeBarGegner(GameObject owner, Handler handler, HUD hud, int Leben) {
		super(owner.x / 2, owner.y - 10, ID.Back);
		
		this.live = Leben;
		this.handler = handler;
		this.owner = owner;
		this.setHud(hud);
		fixX = 0;
		fixY = -10;
		if (owner.id == ID.NormalerGegner) {
			live = 3;
		}
		if (owner.id == ID.SmartGegner) {
			live = 3;
		}
	}

	@Override
	public void tick() {
		x = owner.getX() + fixX;
		y = owner.getY() + fixY;
		if (!handler.objects.contains(owner)) {
			removeThis();
		}
	}

	@Override
	public Rectangle getBounds() {
		return rec;
	}

	@Override
	public void removeThis() {
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}
}