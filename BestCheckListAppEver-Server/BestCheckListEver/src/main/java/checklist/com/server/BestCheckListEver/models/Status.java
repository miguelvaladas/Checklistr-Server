package checklist.com.server.BestCheckListEver.models;

public enum Status {

	DEFAULT("DEFAULT"), RED("RED"), YELLOW("YELLOW"), GREEN("GREEN");

	private String stringValue;

	Status(String stringValue) {
		this.stringValue = stringValue;
	}

	@Override
	public String toString() {
		return stringValue;
	}

}
