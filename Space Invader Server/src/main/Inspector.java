package main;

public class Inspector implements Runnable {

	private boolean isProofing = false;

	@Override
	public void run() {
		while (isProofing) {
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Verbindung v : MultiplayerServer.al) {
				if (v.stillConnected) {
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
