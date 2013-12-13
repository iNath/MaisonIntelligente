package maisonintelligente.capteur;

public interface ILuminosite {

	/**
	 * Retourne la luminositer en pourcentage percu dans la piece
	 * 
	 * @return un pourcentage
	 */
	public int getLuminosite();
	
	public void addListener(ILuminositeListener listener);
	
	public void removeListener(ILuminositeListener listener);
	
}
