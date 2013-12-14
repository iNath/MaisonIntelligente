package maisonintelligente.acteur;

import com.phidgets.InterfaceKitPhidget;

import com.phidgets.PhidgetException;

public class Ampoule implements IAmpoule {
	
	private int pourcentage;
	InterfaceKitPhidget ik;
	
	public Ampoule() {
		ik = InterfaceKit.getInstance().getIk();
		try {
			pourcentage = calculateLumierre();
		} catch (PhidgetException e) {
			e.printStackTrace();
		} 
	}

	@Override
	public void setLumiere(int pourcentage) {
		System.out.println("\nsetting luminosite : "+pourcentage+"\n");
		try {
			switch(pourcentage) {
				case(0) :   ik.setOutputState(0, false);
							ik.setOutputState(7, false);
							break;
				case(50) :  ik.setOutputState(0, true);
							ik.setOutputState(7, false);
							break;
				case(100) : ik.setOutputState(0, true);
							ik.setOutputState(7, true);
							break;
			}
		} catch (PhidgetException e) {
			e.printStackTrace();
		}

	}

	@Override
	public int getLumiere() {
		return pourcentage;
	}
	
	private int calculateLumierre() throws PhidgetException {
		if(ik.getOutputState(0) && ik.getOutputState(7)) {
			return 100;
		}
		else if (ik.getOutputState(0) || ik.getOutputState(7)) {
			return 50;
		}
		else return 0;
	}

}
