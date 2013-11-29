package maisonintelligente.acteur;

import java.util.ArrayList;

public class VoletStub implements IVolet, Runnable {

	ArrayList<IVoletListener> listeners = new ArrayList<IVoletListener>();
	private int pourcentage = 0;
	
	
	public VoletStub() {
		
	}

	public void setOuvertureVolet(int pourcentage) {
		System.out.println("[Volet] change to: " + pourcentage);
		new Thread(this).start();
		this.pourcentage = pourcentage;
	}

	public int getOuvertureVolet() {
		return this.pourcentage;
	}

	public void addListener(IVoletListener listener) {
		listeners.add(listener);
	}

	public void removeListener(IVoletListener listener) {
		for(int i = 0; i<listeners.size();i++){
			if(listeners.get(i).equals(listeners)){
				listeners.remove(i);
				break;
			}
		}

	}

	public void run() {
		
		for(int i=0;i<listeners.size();i++){
			listeners.get(i).actionStart();
		}
		
		try {
			for(int i=0;i<10;i++){
				System.out.println(".."+(10-i));
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(int i=0;i<listeners.size();i++){
			listeners.get(i).actionEnd();
		}
		
	}

}
