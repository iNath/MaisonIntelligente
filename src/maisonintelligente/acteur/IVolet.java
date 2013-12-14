package maisonintelligente.acteur;

public interface IVolet {

	public void setOuvertureVolet(int pourcentage);
	
	public double getOuvertureVolet();
	
	public void addListener(IVoletListener listener);
	
	public void removeListener(IVoletListener listener);
	
}
