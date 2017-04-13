
package main;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
	
	public Verbindung(ServerSocket ss)  {
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
			stillConnected = true;
			MainFrame.info("Verbindung gefunden");
			variablen(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void variablen(Socket s) {
		try {
		sOutput  = new PrintStream(s.getOutputStream());
		sInput  = new Scanner(s.getInputStream());
		name = sInput.nextLine();
		ip = socket.getInetAddress().getHostAddress();
		MainFrame.playerUpdate(name + " (" + ip + ")");
		sOutput.println("Lies vor");
		System.out.println(name + " ++Name");
		System.out.println(ip + " ++IP");
		empfang();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("--- " + e.getMessage());
		}
	}

	public void empfang() {
		String input = null;
		while(stillConnected) {
			if((input = sInput.nextLine()) != null) {
				if(input.charAt(0) == '.') {
					MainFrame.chatUpdate("[" + name + "] " + input.substring(1, input.length()-1));
				}
			}
		}
	}
	
	
}
