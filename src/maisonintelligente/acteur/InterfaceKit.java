package maisonintelligente.acteur;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.Phidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;
import com.phidgets.event.DetachEvent;
import com.phidgets.event.DetachListener;
import com.phidgets.event.ErrorEvent;
import com.phidgets.event.ErrorListener;

public class InterfaceKit {
	
	private static InterfaceKit instance = null;
	
	private InterfaceKitPhidget ik = null;
	
	private InterfaceKit() {
		super();
		System.out.println(Phidget.getLibraryVersion());
		
		 try {
			ik = new InterfaceKitPhidget();
			ik.openAny();
			System.out.println("[InterfaceKit] Waiting for attachement");
			ik.waitForAttachment();
		} catch (PhidgetException e) {
			e.printStackTrace();
		}
		 
		 ik.addAttachListener(new AttachListener() {
			 
			 public void attached(AttachEvent ae) {
				 System.out.println("[InterfaceKit] The interface Kit is attached");
			 }
		 });
		 
		 ik.addDetachListener(new DetachListener() {
			 
			 public void detached(DetachEvent ae) {
				 System.out.println("[InterfaceKit] The interface Kit is dettached");
			 }
		 });
		 
		 ik.addErrorListener(new ErrorListener() {
			 
			 public void error(ErrorEvent ee) {
				 System.out.println("[InterfaceKit] error event for "+ ee);
			 }
		 });
		
	}
	
	public final static InterfaceKit getInstance() {
        
        if (InterfaceKit.instance == null) {
           
           synchronized(InterfaceKit.class) {
             if (InterfaceKit.instance == null) {
            	 InterfaceKit.instance = new InterfaceKit();
             }
           }
        }
        return InterfaceKit.instance;
    }
	
	public InterfaceKitPhidget getIk() {
		return ik;
	}
	
	

}
