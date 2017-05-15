package main;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MultiplayerServer implements Runnable{

	public ServerSocket server;
	public Handler handler;
	public int port = 4308;
	public int trys = 0;
	public static boolean makeNewOne = true;
	public static ArrayList<Verbindung> al = new ArrayList<Verbindung>();
	
	public void verbinde() {
		while(!MainFrame.stopServer) {
			while(!MainFrame.logins.isSelected());
			System.out.println("Trigged");
				if(makeNewOne) {
					makeNewOne = false;
					MainFrame.info("Neue Verbindung wird hergestellt");
					al.add(new Verbindung(server, handler));
					MainFrame.playerUpdate();
				}
			}
		MainFrame.t1 = null;
		MainFrame.stopServer = true;
		MainFrame.info("Der Server is TOT");
		MainFrame.mps = null;
	}
	
	public MultiplayerServer(Handler handler) {
		if(MainFrame.mps != null) {
			MainFrame.info("Der Server läuft bereits. Stoppe den Server mit '/stop'");
			return;
		}
		MainFrame.playerUpdate();
		this.handler = handler;
		MainFrame.t1 = new Thread(this);
		MainFrame.t1.start();
	}
	
	@Override
	public void run() {
		try {
			new Inspector();
			MainFrame.stopServer = false;
			server = new ServerSocket(port);
			MainFrame.cmd("Server gestartet");
			verbinde();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		MainFrame.stopServer = false;
	}

	public static void sendAll(String message) {
		Verbindung verbindung;
		for(int i = 0; i < MultiplayerServer.al.size(); i++) {
			verbindung = MultiplayerServer.al.get(i);
			if(verbindung.stillConnected && verbindung.socket != null) {
				verbindung.send(message);
			}
		}
	}
}
