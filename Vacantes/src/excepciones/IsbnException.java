package excepciones;

@SuppressWarnings("serial")
public class IsbnException extends Exception {
	
	public IsbnException() {}
	public IsbnException(String ms) {
		super(ms);
	}
}
