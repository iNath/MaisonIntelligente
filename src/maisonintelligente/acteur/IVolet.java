package maisonintelligente.acteur;

public interface IVolet {

	public int setOuvertureVolet(int pourcentage);
	
	public void addListener(IVoletListener listener);
	
	public void removeListener(IVoletListener listener);
	
}
