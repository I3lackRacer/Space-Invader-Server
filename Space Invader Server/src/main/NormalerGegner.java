package main;

import java.awt.Rectangle;
import java.util.Random;

public class NormalerGegner extends GameObject {

	private Handler handler;
	private HUD hud;
	public LifeBarGegner LiveBar;
	public int schusswarscheinlichkeit = 9;
	public boolean beeingTraged = false;

	public NormalerGegner(int x, int y, ID id, Handler handler, HUD hud) {
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
			this.removeThis();

		if (new Random().nextInt(1000) < schusswarscheinlichkeit) {
			handler.addObject(new BulletGegner((int) this.x + (int) this.getBounds().getWidth() / 2,
					(int) (this.y + this.getBounds().getHeight()), ID.BulletGegner, handler, this, hud));
		}
		collusion();
	}

	private void collusion() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					this.killThis();
					if(tempobjekt instanceof Schild) {
						Schild.überlasteterSchild = true;
					}
					hud.score++;
				}
			}
			
			if(tempobjekt.id == ID.NormalerGegner) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt != this) {
						if(tempobjekt instanceof NormalerGegner) {
							NormalerGegner n = (NormalerGegner)tempobjekt;
							n.killThis();
						}
					}
				}
			}
			
			if(tempobjekt.id == ID.AimGegner) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					if(tempobjekt != this) {
						if(tempobjekt instanceof AimGegner) {
							AimGegner a = (AimGegner) tempobjekt;
							a.LiveBar.live--;
							this.killThis();
						}
					}
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
