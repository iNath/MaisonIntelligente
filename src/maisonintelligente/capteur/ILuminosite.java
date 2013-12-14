package maisonintelligente.capteur;

import java.io.IOException;

public interface ILuminosite {

	/**
	 * Retourne la luminositer en pourcentage percu dans la piece
	 * 
	 * @return un pourcentage
	 * @throws IOException 
	 */
	public int getLuminosite();
	
	public void addListener(ILuminositeListener listener);
	
	public void removeListener(ILuminositeListener listener);
	
}
