package ca.ulaval.glo4003.appemployee.domain.user;

public class UserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public UserNotFoundException(String message) {
		super(message);
	}

}