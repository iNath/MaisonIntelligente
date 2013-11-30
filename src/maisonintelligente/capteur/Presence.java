package maisonintelligente.capteur;

import java.util.ArrayList;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

import maisonintelligente.acteur.InterfaceKit;

public class Presence implements IPresence, Runnable{

	ArrayList<IPresenceListener> listeners = new ArrayList<IPresenceListener>();
	boolean estPresent;
	InterfaceKitPhidget ik;
	boolean sensorChanged;
	
	public Presence() {
		ik = InterfaceKit.getInstance().getIk();
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
		for(int i = 0; i<listeners.size();i++){
			if(listeners.get(i).equals(listeners)){
				listeners.remove(i);
				break;
			}
		}
		
	}

	@Override
	public void run() {
		
		while(true) {
			sensorChanged = false;
			
			ik.addSensorChangeListener(new SensorChangeListener() {
				 
				 public void sensorChanged(SensorChangeEvent se) {
					 sensorChanged = true;
					 ik.removeSensorChangeListener(this);
				 }
			});
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(sensorChanged) {
				notifyPresence();
				sensorChanged = false;
			}
		}
	}
	
	private void notifyPresence() {
		for(int i=0;i<listeners.size();i++){
			listeners.get(i).presenceChanged();
		}
		estPresent = !estPresent();
		System.out.println("presence changed to "+estPresent);
	}

}
