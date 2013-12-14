package maisonintelligente.capteur;

public interface IPresence {

	/**
	 * Renvoie true ou false selon si la presence a ete detecter ou pas 
	 * 
	 * @return boolean
	 */
	public boolean estPresent();

	public void addListener(IPresenceListener listener);
	
	public void removeListener(IPresenceListener listener);
	
}
