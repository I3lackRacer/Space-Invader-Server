package main;

import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class AimGegner extends GameObject {

	private Handler handler;
	private HUD hud;
	public int schusswarscheinlichkeit = 3;
	public LifeBarGegner LiveBar;
	public boolean beeingTraged;
	private Random r = new Random();

	public AimGegner(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;

		velX = 0;
		velY = 1;

		LiveBar = new LifeBarGegner(this, handler, hud, 3);
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y >= MainFrame.HEIGHT + 10)
			this.removeThis();
		if (x >= MainFrame.WIDTH)
			removeThis();

//		if (r.nextInt(1000) <= schusswarscheinlichkeit) {
//			debugInfo.lastTrigged = "SPAWN_GEGNER SCHUSS";
//			handler.addObjekt(new BulletGegner((int) x + 16, (int) y + 32, ID.AimBullet, handler, null, hud));
//		}
		shot();
		collusion();
	}

	public void shot() {
		if (r.nextInt(1000) <= schusswarscheinlichkeit) {
			handler.addObject(new BulletGegner((int) x + 16, (int) y + 32, ID.AimBullet, handler, null, hud));
		}
	}

	private void collusion() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					killThis();
					if (tempobjekt instanceof Schild) {
						Schild.überlasteterSchild = true;
					}
					hud.score++;
				}
			}
			if (tempobjekt.id == ID.Bullet) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					Bullet b = (Bullet) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
			if (tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					tempobjekt.removeThis();
				}
			}
			if (tempobjekt.id == ID.BulletSpray) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live--;
					BulletSpray b = (BulletSpray) tempobjekt;
					b.removeThis();
				}
			}
			
			if(tempobjekt.id == ID.Asteroid) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt instanceof Asteroid) {
						Asteroid a = (Asteroid)tempobjekt;
						a.lifeBarGegner.live--;
						a.Live--;
					}
					this.killThis();
				}
			}
			
			if (tempobjekt.id == ID.BulletForced) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live -= 3;
					BulletForced b = (BulletForced) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
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
