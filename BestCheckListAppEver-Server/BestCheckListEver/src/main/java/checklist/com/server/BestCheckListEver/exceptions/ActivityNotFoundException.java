package checklist.com.server.BestCheckListEver.exceptions;

public class ActivityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActivityNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
