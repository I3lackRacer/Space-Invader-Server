package main;

import java.awt.Rectangle;

import main.ID;

public class SpeedGegner extends GameObject {
	
	private Handler handler;
	private HUD hud;
	private LifeBarGegner LiveBar;

	public SpeedGegner(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		
		this.hud = hud;
		this.handler = handler;
		
		velX = 0;

		LiveBar = new LifeBarGegner(this, handler, hud, 3);
		handler.addObject(LiveBar);
		
	}

	public void tick() {
		x += velX;
		y += velY;
		
		if(y >= MainFrame.HEIGHT-32 || y <= 0)  velY *=-1;
		if(x <= 0 || x >= MainFrame.WIDTH-32) velX *=-1;
		collusion();
	}

	private void collusion() {
		for(int i = 0; i<handler.objects.size(); i++) {
			
			GameObject tempobjekt = handler.objects.get(i);
			
			if(tempobjekt.id == ID.Schild) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					killThis();
					hud.score++;
				}
			}
			if(tempobjekt.id == ID.Bullet) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					Bullet b = (Bullet) tempobjekt;
					if(b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
			if(tempobjekt.id == ID.BulletSpray) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					BulletSpray b = (BulletSpray) tempobjekt;
					b.removeThis();
				}
			}
			if(tempobjekt.id == ID.BulletForced) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live -= 3;
					BulletForced b = (BulletForced) tempobjekt;
					if(b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}

	@Override
	public void removeThis() {
		LiveBar.removeThis();
		handler.removeObject(this);
	}
	
	public void killThis() {
		LiveBar.removeThis();
		handler.removeObject(this);
	}

}
