package mundoj.commons.client;

public interface ServiceCallback<T> {
	void execute(T serviceResponse);
}