package maisonintelligente.acteur;

public class AmpouleStub implements IAmpoule {

	private int pourcentage = 0;
	
	public void setLumiere(int pourcentage) {
		this.pourcentage = pourcentage;
		System.out.println("[Ampoule] Lumieres set to " + pourcentage);
	}

	public int getLumiere() {
		return this.pourcentage;
	}

}
