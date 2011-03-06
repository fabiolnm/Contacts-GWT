package mundoj.contacts.domain;

import com.google.gwt.core.client.JavaScriptObject;

public class ContactJso extends JavaScriptObject implements IContact {
	protected ContactJso() { }

	@Override
	public final native int getId()/*-{
		return this.id;
	}-*/;

	@Override
	public final native String getName()/*-{
		return this.name;
	}-*/;
	@Override
	public final native void setName(String name)/*-{
		this.name = name;
	}-*/;

	@Override
	public final native String getType()/*-{
		return this.type;
	}-*/;
	@Override
	public final native void setType(String type)/*-{
		this.type = type;
	}-*/;

	@Override
	public final native String getNumber()/*-{
		return this.number;
	}-*/;
	@Override
	public final native void setNumber(String number)/*-{
		this.number = number;
	}-*/;
}