package main;

public class Inspector implements Runnable{
	
	private boolean isProofing = false;
	
	@Override
	public void run() {
		while(isProofing) {
			for(Verbindung v : MultiplayerServer.al) {
				if(v.stillConnected) {
					v.send("&");
				}
			}
		}
	}
	
	public Inspector() {
		setProofing(true);
		Thread t = new Thread(this);
		t.start();
	}

	public boolean isProof() {
		return isProofing;
	}

	public void setProofing(boolean proof) {
		this.isProofing = proof;
	}

}
