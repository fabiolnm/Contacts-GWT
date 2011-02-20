package mundoj.contacts.client;

public class CallbackException extends Exception {
	public final int status;
	public final String statusText;

	public CallbackException(int status, String statusText) {
		super("Status: " + status + " / " + statusText);
		this.status = status;
		this.statusText = statusText;
	}
}