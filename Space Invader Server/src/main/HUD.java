package main;

import java.util.ArrayList;

public class HUD {
	
	public int level = 1;
	public int score = 0;
	public ArrayList<Player> playerList = new ArrayList<Player>();

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	public int getLevel() {
		return level;
	}
	
	public void addPlayer(Player p) {
		playerList.add(p);
	}
	
	public void addNewPlayer(int x, int y, Handler handler, HUD hud, Verbindung verbindung) {
		Player p = new Player(x, y, ID.Player, handler, hud, verbindung, playerList.size());
		playerList.add(p);
	}
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
}
