package maisonintelligente.capteur;

public interface IConfiguration {

	/**
	 * Va chercher comment l'utilisateur a configurer son mode volet ouvert ou mode volet fermer
	 * 
	 * @return l'etat de la configuration utilisateur
	 */
	public boolean estVoletOuvertActive();

	public void addListener(IConfigurationListener listener);
	
	public void removeListener(IConfigurationListener listener);
	
}
