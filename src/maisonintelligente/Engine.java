package maisonintelligente;

import java.util.LinkedList;
import java.util.Queue;

import com.phidgets.event.SensorChangeEvent;

import maisonintelligente.acteur.Ampoule;
import maisonintelligente.acteur.AmpouleStub;
import maisonintelligente.acteur.IAmpoule;
import maisonintelligente.acteur.IVolet;
import maisonintelligente.acteur.IVoletListener;
import maisonintelligente.acteur.VoletStub;
import maisonintelligente.capteur.Configuration;
import maisonintelligente.capteur.ConfigurationStub;
import maisonintelligente.capteur.IConfiguration;
import maisonintelligente.capteur.IConfigurationListener;
import maisonintelligente.capteur.ILuminosite;
import maisonintelligente.capteur.ILuminositeListener;
import maisonintelligente.capteur.IPresence;
import maisonintelligente.capteur.IPresenceListener;
import maisonintelligente.capteur.LuminositeStub;
import maisonintelligente.capteur.Presence;
import maisonintelligente.capteur.PresenceStub;

public class Engine implements Runnable {
	
	IAmpoule ampouleService = new AmpouleStub();
	//IAmpoule ampouleService = new Ampoule();
	IVolet voletService = new VoletStub();
	
	
	//IConfiguration configurationService = new ConfigurationStub();
	IConfiguration configurationService = new Configuration();
	
	ILuminosite luminositeService = new LuminositeStub();
	IPresence presenceService = new PresenceStub();
	//IPresence presenceService = new Presence();
	
	private boolean isBusy = false;
	private boolean stateChanged = false;
	private IVoletListener voletListener;
	
	final private int SEUIL_LUMINOSITE = 70;
	
	public Engine(){
		
		voletListener = new IVoletListener() {
			public void actionStart() { }
			public void actionEnd() { voletActionEndedHandler(); }
		};
		voletService.addListener(voletListener);
		
		tryDoingActions();
		
		configurationService.addListener(new IConfigurationListener() {
			public void configurationChanged() {
				stateChanged = true;
				tryDoingActions();
			}
		});
		luminositeService.addListener(new ILuminositeListener() {
			public void luminositeChanged() {
				stateChanged = true;
				tryDoingActions();
			}
		});
		presenceService.addListener(new IPresenceListener() {
			public void presenceChanged() {
				stateChanged = true;
				tryDoingActions();
			}
		});
	}

	private void voletActionEndedHandler() {
		if(luminositeService.getLuminosite() >= SEUIL_LUMINOSITE){
			voletService.removeListener(voletListener);
			actionEndedHandler();
		} else {
			// Si on est toujours pas bon, on recommence le calcul
			// A condition qu'on ai pas déjà tout ouvert..
			if(ampouleService.getLumiere() < 100 && voletService.getOuvertureVolet() < 100){
				doActions();
			} else {
				// Sinon on arrete la
				actionEndedHandler();
			}
		}
	}

	private void actionEndedHandler() {
		logState();
		this.isBusy = false;
		
		// Si l'etat de la maison a changé on relance les calculs
		if(stateChanged){
			tryDoingActions();
		}
	}

	private synchronized boolean tryDoingActions(){
		if(this.isBusy == true){
			return false;
		}
		
		this.isBusy = true;
		
		doActions();
		
		return true;
	}
	
	private void doActions(){

		System.out.println("[Engine] Changement d'état commence");
		
		if(!presenceService.estPresent()){
			ampouleService.setLumiere(0);
			voletService.setOuvertureVolet(0);
		} else {
			if(configurationService.estVoletOuvertActive()){
				// Si les volets ne sont pas encore ouvert
				if(voletService.getOuvertureVolet() == 0){
					voletService.setOuvertureVolet(100);
					ampouleService.setLumiere(50);
				} else {
					// Si les volets sont ouverts
					
					// Si la luminosité n'est toujours pas suffisante
					if(luminositeService.getLuminosite() < SEUIL_LUMINOSITE){
						// Alors on allume les lumieres a fond
						ampouleService.setLumiere(100);
					}
				}
			} else {
				voletService.setOuvertureVolet(0);
				ampouleService.setLumiere(100);
			}
		}
	}
	
	private void logState() {
		System.out.println("[ Amp ][ Vol ]|[ Cfg ][ Lum ][ Pst ]");
		System.out.println("| " + ampouleService.getLumiere() + " |"
				+ "| " + voletService.getOuvertureVolet() + " |"
				+ "|| " + configurationService.estVoletOuvertActive() + "|"
				+ "| " + luminositeService.getLuminosite() + " |"
				+ "| " + presenceService.estPresent() + "|");
		
	}

	public void run() {
		
	}


}