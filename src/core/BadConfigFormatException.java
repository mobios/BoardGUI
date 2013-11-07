package core;

public class BadConfigFormatException extends Exception { // Exception because we don't know what kind of file the user
														  /**
	 * 
	 */
	private static final long serialVersionUID = -2260682783508379341L;

	// may try to load the error should be thrown to let them know
	public BadConfigFormatException() {					  // the program cannot work with that file
		super("Unspecified error in configuration loading.");
	}

	public BadConfigFormatException(String string) {
		super(string);
	}
	
		
}
