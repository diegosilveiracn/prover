package analyzer;

public class CompilationException extends Exception {

	private static final long serialVersionUID = 4332111436989340845L;

	public CompilationException() {
		super();
	}
	
	public CompilationException(String message) {
		super(message);
	}
	
	public CompilationException(Throwable cause) {
		super(cause);
	}
	
	public CompilationException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
