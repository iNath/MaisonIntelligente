package maisonintelligente;

import maisonintelligente.acteur.IVolet;
import maisonintelligente.acteur.Volet;
import maisonintelligente.acteur.VoletListener;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//new Engine();
		IVolet voletService = new Volet();
		VoletListener voletListener = new VoletListener();
		voletService.addListener(voletListener);
		
		voletService.setOuvertureVolet(0);

		voletService.setOuvertureVolet(220);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	//	voletService.setOuvertureVolet(0);
	}

}
