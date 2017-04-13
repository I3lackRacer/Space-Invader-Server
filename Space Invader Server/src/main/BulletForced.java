package main;

import java.awt.Rectangle;

import main.ID;

public class BulletForced extends GameObject {
	
	private Handler handler;
	private HUD hud;
	public boolean charged;

	public boolean isCharged() {
		return charged;
	}

	public void setCharged(boolean charged) {
		this.charged = charged;
	}

	public BulletForced(int x, int y, ID id, Handler handler, HUD hud, boolean charged) {
		super(x-2, y-8, id);
		
		this.handler = handler;
		this.setHud(hud);
		this.charged = charged;
		
		velX = 0;
		velY = -9;
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0)handler.removeObject(this);;
		if(x <= 0 || x >= MainFrame.WIDTH-16) handler.removeObject(this);
		collision();
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 3, 32);
	}
	
	private void collision() {
		for(int i = 0; i < handler.objects.size(); i++) {
			
			GameObject tempobjekt = handler.objects.get(i);
			
			if(tempobjekt.id == ID.GegnerSchuss) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					handler.removeObject(tempobjekt);
				}
			}
		}
	}

	@Override
	public void removeThis() {
		handler.removeObject(this);
	}

	public HUD getHud() {
		return hud;
	}

	public void setHud(HUD hud) {
		this.hud = hud;
	}

}
