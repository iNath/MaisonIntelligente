package maisonintelligente.acteur;

public class VoletListener implements IVoletListener {

	@Override
	public void actionStart() {
		System.out.println("[VoletListener] Start of shutters action ...");
		
	}

	@Override
	public void actionEnd() {
		System.out.println("[VoletListener] End of shutters action ...");
		
	}

}
