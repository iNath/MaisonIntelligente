package maisonintelligente.acteur;

import java.util.ArrayList;

import com.phidgets.PhidgetException;
import com.phidgets.ServoPhidget;

public class Volet implements IVolet{
	
	ArrayList<IVoletListener> listeners = new ArrayList<IVoletListener>();
	private double pourcentage;
	ServoPhidget servo;
	
	public Volet(){
		try {
			System.out.println("ok");
			servo = new ServoPhidget();
			servo.openAny();
			System.out.println("[Volet] Waiting for attachment with shutters ...");
			
			servo.waitForAttachment();			
			System.out.println("[Volet] Attachment with shutters ok");
			
			System.out.println("[Volet] Init : Closing shutters ...");
			servo.setPosition(0,0);
			pourcentage = 0;

		} catch (PhidgetException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setOuvertureVolet(int newp) {		
		int i;
		double actp ;
	
		actp = pourcentage;
			
		for( i = 0; i<listeners.size();i++){
			listeners.get(i).actionStart();
		}
		
		if(actp > newp)
		{
			System.out.println("[Volet] Closing shutters ...");
			for(i=(int)actp; i>= newp; i--)
			{
				try {
					servo.setPosition(0, i);
				} catch (PhidgetException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}					
			}		
		}
		else
		{
			System.out.println("[Volet] Opening shutters ...");
			for(i=(int)actp; i<= newp; i++)
			{
				try {
					servo.setPosition(0, i);
				} catch (PhidgetException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
						
			}
		}
		for( i = 0; i<listeners.size();i++){
			listeners.get(i).actionEnd();
		}
		pourcentage = newp;
}

	@Override
	public double getOuvertureVolet() {		
		return pourcentage;
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
}
