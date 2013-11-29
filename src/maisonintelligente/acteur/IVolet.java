package maisonintelligente.acteur;

public interface IVolet {

	public void setOuvertureVolet(int pourcentage);
	
	public int getOuvertureVolet();
	
	public void addListener(IVoletListener listener);
	
	public void removeListener(IVoletListener listener);
	
}
