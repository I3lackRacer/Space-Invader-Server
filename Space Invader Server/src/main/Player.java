package main;

import java.awt.Rectangle;

import main.ID;

public class Player extends GameObject {

	public float höhe;
	public float breite;
	private HUD hud;
	Handler handler;
	public static int REDSTRENGH = 10, REDTIME = 5;
	public static int magazinSize = 24;
	public static int bulletRemain = magazinSize;
	public Verbindung verbindung;
	public int verbindungsNummer;

	public Player(int x, int y, ID id, Handler handler, HUD hud, Verbindung verbindung, int verbindungsNummer) {
		
		super(x, y, id);
		this.verbindung = verbindung;
		this.handler = handler;
		this.setHud(hud);
		this.verbindungsNummer = verbindungsNummer;
	}

	public void tick() {
		x = MainFrame.clamp(x, 0, MainFrame.WIDTH - 37);
		y = MainFrame.clamp(y, 0, MainFrame.HEIGHT - 60);

		breite = y;
		höhe = x;

		collision();
	}

	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, 32, 32);
	}

	private void collision() {
		for (int i = 0; i < handler.objects.size(); i++) {

			GameObject tempobjekt = handler.objects.get(i);

			if (tempobjekt.getId() == ID.NormalerGegner) {
				if (new Rectangle((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000)
						.intersects(tempobjekt.getBounds())) {
					NormalerGegner nb = (NormalerGegner) tempobjekt;
					nb.beeingTraged = true;
				} else {
					NormalerGegner nb = (NormalerGegner) tempobjekt;
					nb.beeingTraged = false;
				}
				if (getBounds().intersects(tempobjekt.getBounds())) {
					verbindung.Leben-=35;
					tempobjekt.removeThis();

					handler.removeObject(tempobjekt);
				}
			}

			if (tempobjekt.getId() == ID.AimGegner) {
				if (new Rectangle((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000).intersects(tempobjekt.getBounds())) {
					if (tempobjekt instanceof AimGegner) {
						AimGegner nb = (AimGegner) tempobjekt;
						nb.beeingTraged = true;
					}
				} else {
					if (tempobjekt instanceof AimGegner) {
						AimGegner nb = (AimGegner) tempobjekt;
						nb.beeingTraged = false;
					}
				}
				if (getBounds().intersects(tempobjekt.getBounds())) {
					verbindung.Leben-=35;
					tempobjekt.removeThis();
					handler.removeObject(tempobjekt);
				}
			}
			
			if (tempobjekt.getId() == ID.SmartGegner) {
				if (new Rectangle((int) (x + getBounds().getWidth() / 2), (int) y - 2000, 1, 2000).intersects(tempobjekt.getBounds())) {
					if (tempobjekt instanceof SmartGegner) {
						SmartGegner nb = (SmartGegner) tempobjekt;
						nb.beeingTraged = true;
					}
				} else {
					if (tempobjekt instanceof SmartGegner) {
						SmartGegner nb = (SmartGegner) tempobjekt;
						nb.beeingTraged = false;
					}
				}
				if (getBounds().intersects(tempobjekt.getBounds())) {
					verbindung.Leben-=35;
					tempobjekt.removeThis();
					handler.removeObject(tempobjekt);
				}
			}

			if (tempobjekt.id == ID.SpeedGegner) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					verbindung.Leben-=50;
					tempobjekt.removeThis();
					handler.removeObject(tempobjekt);
				}
			}

			if (tempobjekt.id == ID.GegnerSchuss) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					verbindung.Leben-=10;
					handler.removeObject(tempobjekt);
				}
			}

			if (tempobjekt.getId() == ID.Asteroid) {
				if (getBounds().intersects(tempobjekt.getBounds())) {
					Asteroid a = (Asteroid) tempobjekt;
					a.killThis();
					verbindung.Leben-=50;
				}
			}
		}
	}

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
