package maisonintelligente.capteur;

public interface IConfiguration {

	/**
	 * Va chercher comment l'utilisateur a configuré son mode volet ouvert ou mode volet fermé
	 * 
	 * @return l'état de la configuration utilisateur
	 */
	public boolean estVoletOuvertActive();

	public void addListener(IConfigurationListener listener);
	
	public void removeListener(IConfigurationListener listener);
	
}
