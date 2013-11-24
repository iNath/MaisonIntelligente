package maisonintelligente.acteur;

import java.util.ArrayList;

public class VoletStub implements IVolet, Runnable {

	ArrayList<IVoletListener> listeners = new ArrayList<IVoletListener>();
	
	@Override
	public void setOuvertureVolet(int pourcentage) {
		new Thread(this).start();
	}

	@Override
	public void addListener(IVoletListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(IVoletListener listener) {
		for(int i = 0; i<listeners.size();i++){
			if(listeners.get(i).equals(listeners)){
				listeners.remove(i);
				break;
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		for(int i=0;i<listeners.size();i++){
			listeners.get(i).actionStart();
		}
		
		try {
			this.wait((long) Math.floor(Math.random() * 1000 * 5));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for(int i=0;i<listeners.size();i++){
			listeners.get(i).actionEnd();
		}
		
	}

}
