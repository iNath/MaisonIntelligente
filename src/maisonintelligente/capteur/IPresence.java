package maisonintelligente.capteur;

public interface IPresence {

	/**
	 * Renvoie true ou false selon si la présence a été detecté ou pas 
	 * 
	 * @return boolean
	 */
	public boolean estPresent();

	public void addListener(IPresenceListener listener);
	
	public void removeListener(IPresenceListener listener);
	
}
