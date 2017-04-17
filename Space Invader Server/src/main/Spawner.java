package main;

import java.util.Random;

public class Spawner {
	
	public int weite, höhe;
	public int NormalerGegner_zufall = 10, AimGegner_zufall = 3, Asteroid_zufall = 3;
	public Random r = new Random();
	public Handler handler;
	public HUD hud;
	
	public Spawner(int weite, int höhe, Handler handler, HUD hud) {
		this.weite = weite;
		this.höhe = höhe;
		this.handler = handler;
		this.hud = hud;
	}
	
	public void sendObject(GameObject tmp) {
		String finalString = "";
		if(tmp.getId() == ID.NormalerGegner) {
			finalString = "n";
		}
		if(tmp.getId() == ID.AimGegner) {
			finalString = "a";
		}
		if(tmp.getId() == ID.Asteroid) {
			finalString = "m";
		}
		MultiplayerServer.sendAll(finalString + ( (int)tmp.getX()) + ";" + ( (int)tmp.getY()));
	}

	public void tick() {
		// Normaler Gegner
		if(r.nextInt(1000) <= NormalerGegner_zufall) {
			NormalerGegner n = new NormalerGegner(r.nextInt(weite-32), -32, ID.NormalerGegner, handler, hud);
			handler.addObject(n);
			sendObject(n);
		}
		
		//AimGegner
		if(r.nextInt(1000) <= AimGegner_zufall) {
			AimGegner a = new AimGegner(r.nextInt(weite-32), -32, ID.AimGegner, handler, hud);
			handler.addObject(a);
			sendObject(a);
		}
		
		//Asteroid
		if(r.nextInt(1000) <= Asteroid_zufall) {
			Asteroid a = new Asteroid(r.nextInt(weite-32), -32, ID.Asteroid, handler, hud);
			handler.addObject(a);
			sendObject(a);
		}
	}
}
