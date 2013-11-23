package maisonintelligente.acteur;

public interface IAmpoule {

	/**
	 * Défini la quantité de lumière à allumer
	 * 0 -> Tout éteind
	 * 100 -> Tout allumé
	 * 
	 * @param pourcentage
	 */
	public void setLumiere(int pourcentage);
	
}
