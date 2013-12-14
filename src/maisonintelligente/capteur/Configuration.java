package maisonintelligente.capteur;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Configuration implements IConfiguration, Runnable {

	ArrayList<IConfigurationListener> listeners = new ArrayList<IConfigurationListener>();
	boolean modeActif;
	
	
	
	public Configuration() {
		modeActif = false;
		new Thread(this).start();
	}

	@Override
	public boolean estVoletOuvertActive() {
		
		return modeActif;
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
			
			boolean newModeActif = this.modeActif;
			
			// On va lire le fichier
			URL url;
			try {
				url = new URL("http://nath973.no-ip.org/maisonintelligente/get.php");
			
				InputStream is = url.openStream();
				JsonReader rdr = Json.createReader(is);
				JsonObject obj = rdr.readObject();
				newModeActif = obj.getBoolean("valeur");
			} catch(Exception e){
				e.printStackTrace();
			}
			
			if(newModeActif != this.modeActif){
				this.modeActif = newModeActif;
				System.out.println("Utilisateur a changé son mode à " + modeActif);
			
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).configurationChanged();
				}
			}
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
