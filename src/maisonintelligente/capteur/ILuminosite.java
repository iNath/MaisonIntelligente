package maisonintelligente.capteur;

public interface ILuminosite {

	/**
	 * Retourne la luminosité en pourcentage perçu dans la piece
	 * 
	 * @return un pourcentage
	 */
	public int getLuminosite();
	
	public void addListener(ILuminositeListener listener);
	
	public void removeListener(ILuminositeListener listener);
	
}
