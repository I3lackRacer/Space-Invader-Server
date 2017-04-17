package main;
import java.util.ArrayList;
import java.util.LinkedList;

public class Handler {
	
	public LinkedList<GameObject> objects = new LinkedList<GameObject>();
	public ArrayList<String> player = new ArrayList<String>();
	
	public Handler() {
		
	}
	
	public void addObject(GameObject tmp) {
		objects.add(tmp);
	}
	
	public void removeObject(GameObject tmp) {
		objects.remove(tmp);
	}
	
	public int size() {
		return objects.size();
	}
	
	public GameObject getDirect(ID tmp) {
		for(int i = 0; i < size(); i++) {
			if(objects.get(i).getId() == tmp) {
				return objects.get(i);
			}
		}
		return null;
	}
	
	public GameObject getObject(int i) {
		return objects.get(i);
	}
	
	public int getPosition(GameObject tmp) {
		for(int i = 0; i < size(); i++) {
			if(objects.get(i) == tmp) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean exists(GameObject tmp) {
		for(int i = 0; i < size(); i++) {
			if(objects.get(i) == tmp) {
				return true;
			}
		}
		return false;
	}

	public void tick() {
		for(int i = 0; i < objects.size(); i++) {
			objects.get(i).tick();
		}
	}

}
