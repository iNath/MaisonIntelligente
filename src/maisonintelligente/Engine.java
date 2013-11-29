package maisonintelligente;

import java.util.LinkedList;
import java.util.Queue;

import maisonintelligente.acteur.AmpouleStub;
import maisonintelligente.acteur.IAmpoule;
import maisonintelligente.acteur.IVolet;
import maisonintelligente.acteur.IVoletListener;
import maisonintelligente.acteur.VoletStub;
import maisonintelligente.capteur.ConfigurationStub;
import maisonintelligente.capteur.IConfiguration;
import maisonintelligente.capteur.IConfigurationListener;
import maisonintelligente.capteur.ILuminosite;
import maisonintelligente.capteur.ILuminositeListener;
import maisonintelligente.capteur.IPresence;
import maisonintelligente.capteur.IPresenceListener;
import maisonintelligente.capteur.LuminositeStub;
import maisonintelligente.capteur.PresenceStub;

public class Engine implements Runnable {
	
	IAmpoule ampouleService = new AmpouleStub();
	IVolet voletService = new VoletStub();
	
	IConfiguration configurationService = new ConfigurationStub();
	ILuminosite luminositeService = new LuminositeStub();
	IPresence presenceService = new PresenceStub();
	
	private boolean isBusy = false;
	private IVoletListener voletListener;
	
	public Engine(){
		
		voletListener = new IVoletListener() {
			public void actionStart() { }
			public void actionEnd() { voletActionEndedHandler(); }
		};
		voletService.addListener(voletListener);
		
		doActions();
		
		configurationService.addListener(new IConfigurationListener() {
			public void configurationChanged() {
				doActions();
			}
		});
		luminositeService.addListener(new ILuminositeListener() {
			public void luminositeChanged() {
				doActions();
			}
		});
		presenceService.addListener(new IPresenceListener() {
			public void presenceChanged() {
				doActions();
			}
		});
	}

	private void voletActionEndedHandler() {
		voletService.removeListener(voletListener);
		actionEndedHandler();
	}

	private void actionEndedHandler() {
		logState();
		this.isBusy = false;
		//if(hasNextAction) doActions();
	}

	private synchronized boolean doActions(){
		if(this.isBusy == true){
			return false;
		}
		System.out.println("[Engine] Changement d'état commence");
		
		
		this.isBusy = true;
		
		if(!presenceService.estPresent()){
			ampouleService.setLumiere(0);
			voletService.setOuvertureVolet(0);
		} else {
			if(configurationService.estVoletOuvertActive()){
				voletService.setOuvertureVolet(100);
				ampouleService.setLumiere(50);
			} else {
				voletService.setOuvertureVolet(0);
				ampouleService.setLumiere(100);
			}
		}
		
		return true;
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