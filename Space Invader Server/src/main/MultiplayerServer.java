package main;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class MultiplayerServer implements Runnable{

	public ServerSocket server;
	public int port = 4308;
	public int trys = 0;
	public static ArrayList<Verbindung> al = new ArrayList<Verbindung>();
	
	public void verbinde() {
		al.add(new Verbindung(server));
		MainFrame.info("Verbindungen werden gesucht");
		while(!MainFrame.stopServer) {
			while(!MainFrame.logins.isSelected());
				if(al.get(al.size()-1).stillConnected) {
					MainFrame.info("Neue Verbindung wird hergestellt");
					MainFrame.playerUpdate(al.get(al.size()-1).name);
					al.add(new Verbindung(server));
				}
			}
		MainFrame.t1 = null;
		MainFrame.stopServer = false;
		MainFrame.info("Der Server is TOT");
	}
	
	public MultiplayerServer() {
		if(MainFrame.mps != null) {
			MainFrame.info("Der Server läuft bereits. Stoppe den Server mit '/stop'");
			return;
		}
		MainFrame.t1 = new Thread(this);
		MainFrame.t1.start();
	}
	
	public void stop() {
		try {
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			new Inspector();
			server = new ServerSocket(port);
			MainFrame.cmd("Server gestartet");
			verbinde();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendAll(String message) {
		Verbindung verbindung;
		for(int i = 0; i < MultiplayerServer.al.size(); i++) {
			verbindung = MultiplayerServer.al.get(i);
			if(verbindung.stillConnected) {
				verbindung.send(message);
			}
		}
	}
}
