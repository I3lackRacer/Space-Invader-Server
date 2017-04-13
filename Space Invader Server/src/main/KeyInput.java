package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
			if (MainFrame.input.getText().charAt(0) != '/') {
				MainFrame.chat(MainFrame.input.getText());
			} else {
				MainFrame.command(MainFrame.input.getText());
			}
			MainFrame.input.setText("");
			}catch(Exception i) {
			}
		}
	}
 
	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

}
