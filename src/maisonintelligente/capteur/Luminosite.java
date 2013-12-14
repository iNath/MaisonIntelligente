package maisonintelligente.capteur;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


import com.github.sarxos.webcam.Webcam;


public class Luminosite implements ILuminosite, Runnable {
	
	ArrayList<ILuminositeListener> listeners = new ArrayList<ILuminositeListener>();
	int luminosite;
	
	
	
	private static double getLuminositePixel(BufferedImage img, int x, int y) {
		int argb = img.getRGB(x, y);
		double Y;
		int rgb[] = new int[] {
		    (argb >> 16) & 0xff, //red
		    (argb >>  8) & 0xff, //green
		    (argb      ) & 0xff  //blue
		};
		Y = 0.2126*rgb[0] + 0.7152*rgb[1] + 0.0722*rgb[2];
		return Y;
		}
	
	
	public Luminosite(){
		luminosite = 50;
		new Thread(this).start();
	}
	
	@Override
	public int getLuminosite()  {
		Webcam hercules = Webcam.getWebcams().get(1);
	 	hercules.open();	
	    BufferedImage img =hercules.getImage();
	    int l;
	    int w = img.getWidth();
		int h = img.getHeight();
		double L=0;
		for (int x=0; x<w; x++)
			for (int y=0; y<h; y++)
			{    		
				L = L+ getLuminositePixel(img, x, y);      		
			}
		l= (int) (((L/(w*h))/255)*100);
		return l;
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
			int newLuminosite = getLuminosite();
			
			if(newLuminosite != this.luminosite){
				this.luminosite = newLuminosite;
				System.out.println("[Luminosite] La luminosite a changer : " + luminosite);
			
				for(int i=0;i<listeners.size();i++){
					listeners.get(i).luminositeChanged();
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
