package maisonintelligente;

import java.util.LinkedList;
import java.util.Queue;

import com.phidgets.event.SensorChangeEvent;

import maisonintelligente.acteur.Ampoule;
import maisonintelligente.acteur.AmpouleStub;
import maisonintelligente.acteur.IAmpoule;
import maisonintelligente.acteur.IVolet;
import maisonintelligente.acteur.IVoletListener;
import maisonintelligente.acteur.Volet;
import maisonintelligente.acteur.VoletListener;
import maisonintelligente.acteur.VoletStub;
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
	
	//IAmpoule ampouleService = new AmpouleStub();
	/*Permet de controller la lumiere de la piece*/
	IAmpoule ampouleService = new Ampoule();
	
	/*Permet de controller les volet de la piece*/
	IVolet voletService = new Volet();
	
	/*Permet de configurer le mode de luminosite de la piece(volets, lumiere)*/
	IConfiguration configurationService = new ConfigurationStub();
	
	/*Permet d'avoir la luminosite de la piece*/
	ILuminosite luminositeService = new LuminositeStub();
	/*Permet de detecter la presence d'une personne dans la piece*/
	//IPresence presenceService = new PresenceStub();
	IPresence presenceService = new Presence();
	
	/*Permet de savoir qd les vole*/
	private IVoletListener voletListener;
	
	private boolean isBusy = false;
	
	public Engine(){
		
		System.out.println("[Engine] start Engine");
		
		voletListener = new VoletListener();
		voletService.addListener(voletListener);
		
		//doActions();
		
		/*Ajouter un listener pour savoir si la configuration a changer*/
		configurationService.addListener(new IConfigurationListener() {
			public void configurationChanged() {
				doActions();
			}
		});
		/*Ajouter un listener pour savoir si la luminosite à changer*/
		luminositeService.addListener(new ILuminositeListener() {
			public void luminositeChanged() {
				doActions();
			}
		});
		/*Ajouter un listener pour savoir si la presence de l'utilisateur a changer*/
		presenceService.addListener(new IPresenceListener() {
			public void presenceChanged() {
				doActions();
			}
		});
	
		System.out.println("[Engine] end Engine");
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
		System.out.println("[Engine] start do action");
		
		
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
		
		System.out.println("[Engine] end do action");
		
		return true;
	}
	
	private void logState() {
		System.out.println("[ Amp ][ Vol ]|[ Cfg ][ Lum ][ Pst ]");
		System.out.println("|  ampouleService.getLumiere() +  |"
				+ "| " + voletService.getOuvertureVolet() + " |"
				+ "|| " + configurationService.estVoletOuvertActive() + "|"
				+ "| " + luminositeService.getLuminosite() + " |"
				+ "| " + presenceService.estPresent() + "|");
		
	}

	public void run() {
		
	}


}