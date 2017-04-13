package main;

import java.awt.Rectangle;
import java.util.Random;

import main.ID;

public class BulletSpray extends GameObject {
	
	private Handler handler;
	private HUD hud;
	private Random r = new Random();
	int lifetime = r.nextInt(200);
	
	public BulletSpray(int x, int y, ID id, Handler handler, HUD hud) {
		super(x, y, id);
		
		this.handler = handler;
		this.setHud(hud);
		
		velX = (r.nextFloat()-0.5F)*22;
		velY = (r.nextFloat()-0.5F)*22;
	}

	public void tick() {
		if(lifetime <= 0) {
			removeThis();
		}else{
			lifetime--;
		}
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= MainFrame.HEIGHT)velY *= -1;
		if(x <= 0 || x >= MainFrame.WIDTH) velX *= -1;
		collision();
	}

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 3, 3);
	}
	
	private void collision() {
		for(int i = 0; i < handler.objects.size(); i++) {
			
			GameObject tempobjekt = handler.objects.get(i);
			
			if(tempobjekt.id == ID.NormalerGegner) {
				if(getBounds().intersects(tempobjekt.getBounds())) {
					
				}
			}
		}
	}

	@Override
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
