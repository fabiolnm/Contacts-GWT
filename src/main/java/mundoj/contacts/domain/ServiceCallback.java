package mundoj.contacts.domain;

public interface ServiceCallback<T> {
	void execute(T serviceResponse);
}