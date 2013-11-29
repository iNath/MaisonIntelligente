package maisonintelligente.capteur;

import java.util.ArrayList;

public class PresenceStub implements IPresence, Runnable {

	ArrayList<IPresenceListener> listeners = new ArrayList<IPresenceListener>();
	boolean estPresent;
	
	public PresenceStub(){
		estPresent = false;
		new Thread(this).start();
	}
	
	@Override
	public boolean estPresent() {
		return estPresent;
	}

	@Override
	public void addListener(IPresenceListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IPresenceListener listener) {
		// TODO Auto-generated method stub		
		for(int i = 0; i<listeners.size();i++){
			if(listeners.get(i).equals(listeners)){
				listeners.remove(i);
				break;
			}
		}
	}

	@Override
	public void run() {
		
		while(true){
			
			boolean newEstPresent = Math.random() > 0.5;
			
			if(newEstPresent != this.estPresent){
				this.estPresent = newEstPresent;
				System.out.println("La presence a changé: présent = " + estPresent);
			
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).presenceChanged();
				}
			}
			
			try {
				Thread.sleep((long) Math.floor(Math.random() * 1000 * 60));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
