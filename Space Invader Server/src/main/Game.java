package main;

public class Game implements Runnable {
	
	public static boolean MasterRunning = false;
	public Spawner spawner;
	public Handler handler;
	public HUD hud;
	
	public Game() {
		handler = new Handler();
		hud = new HUD();
		spawner = new Spawner(MainFrame.WIDTH, MainFrame.HEIGHT, handler, hud);
		MasterRunning = true;
		Thread t1 = new Thread(this);
		t1.start();
	}

	@Override
	public void run() {
		long letztesMal = System.nanoTime();
		double anzahlvonTicks = 60.0;
		double ns = 1000000000 / anzahlvonTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (MasterRunning) {
			delta += (System.nanoTime() - letztesMal) / ns;
			letztesMal = System.nanoTime();
			while (delta >= 1) {
				tick();
				delta--;
			}
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}

	public void tick() {
		spawner.tick();
	}
	
	public void stop() {
		
	}
}
