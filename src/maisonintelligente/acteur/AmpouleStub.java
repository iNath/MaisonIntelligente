package maisonintelligente.acteur;

public class AmpouleStub implements IAmpoule {

	public void setLumiere(int pourcentage) {
		System.out.println("[Ampoule] Lumieres set to " + pourcentage);
	}

}
