package maisonintelligente.capteur;

import java.util.ArrayList;

public class ConfigurationStub implements IConfiguration, Runnable {

	ArrayList<IConfigurationListener> listeners = new ArrayList<IConfigurationListener>();
	boolean modeActif;
	
	
	
	public ConfigurationStub() {
		modeActif = false;
		new Thread(this).start();
	}

	@Override
	public boolean estVoletOuvertActive() {
		
		return estVoletOuvertActive();
	}

	@Override
	public void addListener(IConfigurationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IConfigurationListener listener) {
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
	
			boolean newModeActif = Math.random() > 0.5;
			
			if(newModeActif != this.modeActif){
			
				System.out.println("Utilisateur a changé son mode à " + modeActif);
			
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).configurationChanged();
				}
			}
			
			try {
				this.wait((long) Math.floor(Math.random() * 1000 * 60));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
