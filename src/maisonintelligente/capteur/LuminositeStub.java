package maisonintelligente.capteur;

import java.util.ArrayList;

public class LuminositeStub implements ILuminosite, Runnable {
	
	ArrayList<ILuminositeListener> listeners = new ArrayList<ILuminositeListener>();
	int luminosite;
	
	public LuminositeStub(){
		luminosite = 50;
		new Thread(this).start();
	}
	
	@Override
	public int getLuminosite() {
		return luminosite;
	}

	@Override
	public void addListener(ILuminositeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(ILuminositeListener listener) {
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
			
			int newLuminosite = (int) Math.floor((Math.random() * 100));
			
			if(newLuminosite != this.luminosite){
				this.luminosite = newLuminosite;
				System.out.println("[LuminositeStub] La luminosite a changer : " + luminosite);
			
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).luminositeChanged();
				}
			}
			
			try {
				Thread.sleep((long) Math.floor(Math.random() * 1000 * 30));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
