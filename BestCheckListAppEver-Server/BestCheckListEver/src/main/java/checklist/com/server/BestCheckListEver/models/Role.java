package checklist.com.server.BestCheckListEver.models;

public enum Role {
	USER("USER"),
	ADMIN("ADMIN");

	private String stringValue;	

	Role(String stringValue){
		this.stringValue = stringValue;
	}

	@Override
	public String toString(){
		return this.stringValue;
	} 

}
