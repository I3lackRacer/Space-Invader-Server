package main;

import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class SmartGegner extends GameObject {

	private Handler handler;
	private HUD hud;
	public int schusswarscheinlichkeit = 3, timer = 10, backtimer = 0, cooldown = 0;
	public LifeBarGegner LiveBar;
	public boolean beeingTraged;
	private boolean alarm = false, left = true;
	private Random r = new Random();

	public SmartGegner(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);

		this.hud = hud;
		this.handler = handler;

		velX = 0;
		velY = 2;

		LiveBar = new LifeBarGegner(this, handler, hud, 3);
	}

	public void tick() {
		if(velX == 0 && velY == 0) {
			velY = 2;
		}
		if(y <= -64) {
			y = -32;
			setVelY(0);
			x = r.nextInt(MainFrame.WIDTH-32);
		}
		if(x >= MainFrame.WIDTH) {
			x = MainFrame.WIDTH-32;
		}
		if(x < 0) {
			x = 0;
		}
		x += velX;
		y += velY;
		if(cooldown > 0) {
			cooldown--;
		}

		if(alarm) {
			if(left) {
				x += -12;
			}
			else {
				x += +12;
			}
			timer--;
			if(timer < 1) {
				alarm = false;
				timer = 10;
			}
		}
		
		if (y >= MainFrame.HEIGHT-32) {
			setVelY(0);
			setVelX(23);
		}
		if (x >= MainFrame.WIDTH-32) {
			setVelX(0);
			setX(MainFrame.WIDTH-32);
			if(y >= MainFrame.HEIGHT-32) {
				setVelY(-23);
			}
		}
		if(y <= 0 && x >= MainFrame.WIDTH-32) {
			backtimer = r.nextInt(35);
			y = 0;
			setVelY(0);
			setVelX(-23);
		}
		if(backtimer > 1) {
			x -= 23;
			backtimer--;
		}
		if(backtimer == 1) {
			backtimer = 0;
			setVelX(0);
			setVelY(2);
		}

		if (new Random().nextInt(1000) <= schusswarscheinlichkeit) {
			handler.addObject(new BulletGegner((int) x + 16, (int) y + 32, ID.AimBullet, handler, null, hud));
		}
		collusion();
	}

	private void collusion() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);
			
			if(tempobjekt.id == ID.Bullet && cooldown == 0|| tempobjekt.id == ID.BulletSpray && cooldown == 0) {
				if(tempobjekt.getBounds().intersects(new Rectangle((int)x, (int)y, (int)(getBounds().getWidth()), (int)(getBounds().getHeight()+50)))) {
					alarm  = true;
					left = r.nextBoolean();
					cooldown = 50;
				}
			}
			
			if(tempobjekt.id == ID.Player) {
				ausweichen(tempobjekt);
			}

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					killThis();
					if(tempobjekt instanceof Schild) {
						Schild.überlasteterSchild = true;
					}
					hud.score++;
				}
				ausweichen(tempobjekt);
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
			
			if(tempobjekt.id == ID.SmartGegner) {
				ausweichen(tempobjekt);
			}
			
			if (tempobjekt.id == ID.GegnerSchuss) {
				ausweichen(tempobjekt);
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
			
			if(tempobjekt.id == ID.NormalerGegner) {
				ausweichen(tempobjekt);
			}
			
			if(tempobjekt.id == ID.AimGegner) {
				ausweichen(tempobjekt);
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
				ausweichen(tempobjekt);
			}
			
			if (tempobjekt.id == ID.BulletForced) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					LiveBar.live -= 3;
					BulletForced b = (BulletForced) tempobjekt;
					if (b.isCharged() == false) {
						b.removeThis();
					}
				}
				ausweichen(tempobjekt);
			}
		}
	}
	
	public void ausweichen(GameObject tempobjekt) {
		//RIGHT
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x+getWidth()), (int)y, (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			alarm = true;
			left = true;
			cooldown = 50;
		}
		//Left
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x-getWidth()), (int)y, (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			alarm = true;
			left= false;
			cooldown = 50;
		}
		//Bottom
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x), (int)(y+getHeight()), (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			alarm = true;
			left= r.nextBoolean();
			cooldown = 50;
			y -= 20;
		}
		//Top
		if(tempobjekt.getBounds().intersects(new Rectangle((int)(x), (int)(y-getHeight()), (int)getBounds().getWidth(),(int)getBounds().getHeight()))) {
			if(velY < 0) {
				alarm = true;
				left= true;
				cooldown = 50;
			}
			else {
				alarm = true;
				left= r.nextBoolean();
				y += 20;
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