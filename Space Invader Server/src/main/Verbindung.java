
package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class Verbindung implements Runnable {

	public String name = "";
	public String ip = "";
	public int Leben = 100;
	public Socket socket;
	public Scanner sInput;
	public PrintStream sOutput;
	public Handler handler;
	public int id;
	public HUD hud = MainFrame.game.hud;
	public Player player;
	public boolean stillConnected = false;
	private ServerSocket server;
	private long lastInput = 0;

	public Verbindung(ServerSocket ss, Handler handler) {
		this.id = MultiplayerServer.al.size();
		this.handler = handler;
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
			server.setSoTimeout(20);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		do {
			try {
				socket = server.accept();
			} catch (IOException e) {
				if (e.getMessage() != "Accept timed out") {
					if (e.getMessage() == "Socket is closed") {
						this.stop();
					}
				}

			}
			
		} while (socket == null);
		MultiplayerServer.makeNewOne = true;
		stillConnected = true;
		lastInput = System.currentTimeMillis();
		player = new Player(0, 0, ID.Player, handler, hud, this, id);
		handler.addObject(player);
		MainFrame.info("Verbindung gefunden");
		variablen(socket);
	}

	public void update() {
		for (int i = 0; i < MainFrame.game.handler.size(); i++) {
			GameObject o = MainFrame.game.handler.objects.get(i);
			String finalString = "";
			if (o.getId() == ID.NormalerGegner) {
				finalString = "n";
			}
			if (o.getId() == ID.AimGegner) {
				finalString = "a";
			}
			if (o.getId() == ID.Asteroid) {
				finalString = "m";
			}
			if (o.getId() == ID.Player) {
				if (((Player) o).verbindungsNummer != id) {
					finalString = "p" + ((Player) o).verbindungsNummer + ";";
				} else {
					return;
				}
			}
			send(finalString + ((int) o.getX()) + ";" + ((int) o.getY()));
		}
	}

	public void variablen(Socket s) {
		try {
			sOutput = new PrintStream(s.getOutputStream());
			sInput = new Scanner(s.getInputStream());
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
		while (stillConnected) {
			if (sInput.hasNext()) {
				lastInput = System.currentTimeMillis();
				System.out.println(lastInput);
				input = sInput.next();

				if (input.charAt(0) == 'p') {
					int x = Integer.valueOf(input.substring(1).split(";")[0]),
							y = Integer.valueOf(input.substring(1).split(";")[1]);
					player.setX(x);
					player.setY(y);
					for (int i = 0; i < MultiplayerServer.al.size(); i++) {
						Verbindung v = MultiplayerServer.al.get(i);
						if (v.id != this.id) {
							v.send("k" + player.verbindungsNummer + ";" + x + ";" + y);
						}
					}
				}
				if (input.charAt(0) == '.') {
					MainFrame.chatUpdate("[" + name + "] " + input.substring(1, input.length() - 1));
				}
				if (input.charAt(0) == 'b') {
					if (input == "bye") {
						stop();
						return;
					} else {
						Bullet b = new Bullet(Integer.valueOf(input.substring(1).split(";")[0]), Integer.valueOf(input.substring(1).split(";")[1]), ID.Bullet, MainFrame.game.handler, MainFrame.game.hud, false);
						MainFrame.game.handler.addObject(b);
						MultiplayerServer.sendAll("b" + b.x + ";" + b.y);
					}
				}
			}
		}
	}

	public void stop() {
		if (socket != null) {
			try {
				send("bye");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			MainFrame.info("Verbindung von " + name + " getrennt. IP: (" + ip + ")");
		}
		MultiplayerServer.al.remove(this);
		MainFrame.playerUpdate();
	}

	public void send(String finalString) {
		sOutput.append(finalString + "\n");
	}

	public void inspection() {
		long currentTime = System.currentTimeMillis();
		if ((currentTime - lastInput) >= 15000) {
			MainFrame.info(name + " Disconnect: Too Quite (" + (currentTime - lastInput) + ")");
			stop();
		}
	}

	public boolean isMounted() {
		if (socket != null) {
			return true;
		}
		return false;
	}
}
