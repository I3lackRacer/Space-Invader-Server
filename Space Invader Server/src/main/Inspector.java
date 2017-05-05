package main;

public class Inspector implements Runnable {

	private boolean isProofing = false;

	@Override
	public void run() {
		while (isProofing) {
			try {
				Thread.sleep(15*1000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = 0; i < MultiplayerServer.al.size(); i++) {
				Verbindung v = MultiplayerServer.al.get(i);
				if (v.stillConnected && v.socket != null) {
					v.inspection();
				}
			}
		}
	}

	public Inspector() {
		isProofing = true;
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
