package main;

import java.util.Random;

public class Spawn {

	public Handler handler;
	public HUD hud;
	public int lastXPos = 0;
	public Random r = new Random();
	public int eckenkriecher = 0;

	public Spawn(Handler handler, HUD hud) {
		this.handler = handler;
		this.hud = hud;
	}

	public void tick() {
		// NormalerGegner
		if (r.nextInt(1000) <= 9 + (hud.getLevel() * 5)) {
			handler.addObject(new NormalerGegner(r.nextInt(MainFrame.WIDTH-32), -64, ID.NormalerGegner, handler, hud));
		}
		// Asteroid
		if (r.nextInt(1000) <= 1) {
			handler.addObject(new Asteroid(r.nextInt(MainFrame.WIDTH-32), -64, ID.Asteroid, handler, hud));
		}
		// AimGegner
		if (r.nextInt(1000) <= 1) {
			handler.addObject(new AimGegner(r.nextInt(MainFrame.WIDTH-32), -64, ID.AimGegner, handler, hud));
		}
		lastXPos = (int) handler.getDirect(ID.Player).getX();
	}
}