
package main;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class Verbindung implements Runnable{

	public String name = "";
	public String ip = "";
	public int Leben = 100;
	public Socket socket;
	public Scanner sInput;
	public PrintStream sOutput;
	public int id;
	public boolean stillConnected = false;
	private ServerSocket server;
	private long lastInput = 0;
	
	public Verbindung(ServerSocket ss)  {
		MainFrame.info("Verbindung Nr." + MultiplayerServer.al.size());
		Thread t1 = new Thread(this);
		server = ss;
		t1.start();
	}

	@Override
	public void run() {
		Connect();
	}
	
	public void Connect() {
		try {
			socket = server.accept();
			lastInput = System.currentTimeMillis();
			stillConnected = true;
			MainFrame.info("Verbindung gefunden");
			variablen(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		for(int i = 0; i < MainFrame.game.handler.size(); i++) {
			GameObject o = MainFrame.game.handler.objects.get(i);
			String finalString = "";
			if(o.getId() == ID.NormalerGegner) {
				finalString = "n";
			}
			if(o.getId() == ID.AimGegner) {
				finalString = "a";
			}
			if(o.getId() == ID.Asteroid) {
				finalString = "m";
			}
			send(finalString + ( (int)o.getX() ) + ";" + ( (int)o.getY() ) );
		}
	}

	public void variablen(Socket s) {
		try {
		sOutput  = new PrintStream(s.getOutputStream());
		sInput  = new Scanner(s.getInputStream());
		name = sInput.nextLine();
		ip = socket.getInetAddress().getHostAddress();
		MainFrame.playerUpdate();
		sOutput.println("Lies vor");
		update();
		empfang();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	public void empfang() {
		String input = null;
		while(stillConnected) {
			if(sInput.hasNext()) {
				lastInput = System.currentTimeMillis();
				input = sInput.next();
				if(input == "bye") {
					stop();
				}
				if(input.charAt(0) == '.') {
					MainFrame.chatUpdate("[" + name + "] " + input.substring(1, input.length()-1));
				}
				if(input.charAt(0) == 'b') {
					Bullet b = new Bullet(Integer.valueOf(input.substring(1).split(";")[0]), Integer.valueOf(input.substring(1).split(";")[1]), ID.Bullet, MainFrame.game.handler, MainFrame.game.hud, false);
					MainFrame.game.handler.addObject(b);
					MultiplayerServer.sendAll("b" + b.x + ";" + b.y);
				}	
			}
		}
	}
	
	public void stop(){
		try {
			socket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		MultiplayerServer.al.remove(this);
		MainFrame.info("Verbindung von " + name + " getrennt. IP: (" + ip + ")");
		MainFrame.playerUpdate();
	}
	
	public void send(String finalString) {
		sOutput.append(finalString + "\n");
	}

	public void inspection() {
		long currentTime = System.currentTimeMillis();
		if((currentTime - lastInput) >= 2000) {
			stop();
		}
	}
}
