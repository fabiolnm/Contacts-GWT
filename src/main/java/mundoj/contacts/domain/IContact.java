package mundoj.contacts.domain;

public interface IContact {
	int getId();
	
	String getName();
	void setName(String name);
	
	String getType();
	void setType(String type);
	
	String getNumber();
	void setNumber(String number);
}