package main;

import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class BulletGegner extends GameObject {

	private Handler handler;
	private HUD hud;

	public BulletGegner(int x, int y, ID id, Handler handler, NormalerGegner spawner, HUD hud) {
		super(x, y, id);

		this.handler = handler;
		this.setHud(hud);

		velX = 0;
		velY = 9;

		if (id == ID.AimBullet) {
			if (hud.playerList.size() >= 1) {
				float playerX = hud.playerList.get(new Random().nextInt(hud.playerList.size() - 1)).getX() + 16;
				float playerY = hud.playerList.get(new Random().nextInt(hud.playerList.size() - 1)).getY() + 16;

				float sup = (float) Math.sqrt(((playerX - x) * (playerX - x)) + ((playerY - y) * (playerY - y)));
				float speed = 8F;
				velX = ((playerX - x) / sup) * speed;
				velY = ((playerY - y) / sup) * speed;
			}
		}
	}

	public void tick() {
		x += velX;
		y += velY;

		if (y >= MainFrame.HEIGHT)
			handler.removeObject(this);
		if (x <= 0 || x >= MainFrame.WIDTH - 16)
			handler.removeObject(this);
		collision();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 4, 4);
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);
			if (tempobjekt.id == ID.Player) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					Player p = (Player) tempobjekt;
					p.verbindung.Leben -= 11;
					handler.removeObject(this);
				}
			}

			if (tempobjekt.id == ID.Schild) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					handler.removeObject(this);
				}
			}

			if (tempobjekt.id == ID.AimGegner) {
				removeThis();
			}
			if (tempobjekt.id == ID.NormalerGegner) {
				removeThis();
			}
		}
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
