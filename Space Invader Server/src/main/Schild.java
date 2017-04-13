package main;

import java.awt.Rectangle;

import main.ID;

public class Schild extends GameObject {

	private Handler handler;
	private int timer = 5;
	private int color = 255;
	private HUD hud;
	public static boolean überlasteterSchild = false;
	public Player owner;

	public Schild(int x, int y, ID id, Handler handler, HUD hud, Player player) {
		super(x, y, id);

		this.owner = player;
		this.handler = handler;
		this.hud = hud;
		this.x = handler.objects.getFirst().x - 16;
		this.y = handler.objects.getFirst().y - 30;

		if (überlasteterSchild) {
			this.removeThis();
		}
	}

	public void tick() {
		if (überlasteterSchild) {
			this.removeThis();
		}
		if (timer > 1) {
			timer--;
		} else {
			timer = 1;
			if (color > 0) {
				color--;
			}

			if (color <= 1) {
				überlasteterSchild = true;
				Player.setRedHit(250);
				owner.verbindung.Leben -= 10;
				handler.removeObject(this);
				color = 255;
			}

		}

		x = handler.objects.getFirst().x - 16;
		y = handler.objects.getFirst().y - 30;

		collision();
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.id == ID.Bullet || tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					tempobjekt.removeThis();
					if (tempobjekt.id == ID.NormalerGegner) {
						hud.score++;
					}
				}
			}
		}
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 64, 20);
	}

	@Override
	public void removeThis() {
		handler.removeObject(this);
	}

}
