package main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Icon {
	
	public BufferedImage icon() {
		try {
			return ImageIO.read(getClass().getResource("/main/gag.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Bild nicht gefunden.");
		return null;
	}

}
