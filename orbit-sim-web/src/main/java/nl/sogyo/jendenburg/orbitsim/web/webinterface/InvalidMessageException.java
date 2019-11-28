package nl.sogyo.jendenburg.orbitsim.web.webinterface;

public class InvalidMessageException extends Exception 
{
	private static final long serialVersionUID = -4600578590733442913L;

	public InvalidMessageException() {
        super();
    }
	
	public InvalidMessageException(String message) {
        super(message);
    }
	
	public InvalidMessageException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public InvalidMessageException(Throwable cause) {
        super(cause);
    }
}
