package mundoj.contacts.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

public abstract class JsonRequest {
	public abstract void callback(String json);
	public abstract void callback(Throwable e);

	private boolean cancelled;
	private String data = "";

	public void get(String url) {
		execute(RequestBuilder.GET, url + "?" + data, null);
	}
	
	public JsonRequest with(String key, String value) {
		data += key + "=" + value + "&";
		return this;
	}

	public void cancel() {
		cancelled = true;
	}

	private void execute(RequestBuilder.Method method, String url, String requestBody) {
		try {
			new RequestBuilder(method, url).sendRequest(requestBody, new RequestCallback() {
				public void onError(Request request, Throwable exception) {
					if (!cancelled)
						callback(exception);
				}
				public void onResponseReceived(Request request, Response response) {
					if (!cancelled) {
						int status = response.getStatusCode();
						if (status == Response.SC_OK)
							callback(response.getText());
						else callback(new CallbackException(status, response.getStatusText()));
					}
				}
			});
		} catch (RequestException e) {
			if (!cancelled)
				callback(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected<T extends JavaScriptObject> List<T> asList(String json) {
		ArrayList<T> list = new ArrayList<T>();
		JsArray<T> jsArray = (JsArray<T>) eval(json);
		int n = jsArray.length();
		for (int i=0; i<n; i++)
			list.add(jsArray.get(i)); 
		return list;
	}

	protected final native<T extends JavaScriptObject> T eval(String json)/*-{
		return eval("(" + json + ")");
	}-*/;
}