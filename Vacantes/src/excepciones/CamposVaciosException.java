package excepciones;

@SuppressWarnings("serial")
public class CamposVaciosException extends Exception{
	
	public CamposVaciosException() {}
	public CamposVaciosException(String ms) {
		super(ms);
	}
}
