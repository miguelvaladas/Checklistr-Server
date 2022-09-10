package checklist.com.server.BestCheckListEver.exceptions;

public class UserNotFoundException extends RuntimeException {

	public static final long serialVersionUID = 1L;

	public UserNotFoundException(String errorMessage) {
		super(errorMessage);
	}

}
