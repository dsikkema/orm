package org.dsikkema.orm.orm;

public class PropertyDefinition {
	
	private String name;
	private boolean isRequired;
	private Type type;

	public PropertyDefinition(String name, boolean isRequired, Type type) {
		this.name = name;
		this.isRequired = isRequired;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isRequired() {
		return this.isRequired;
	}
	
	public Type getType() {
		return this.type;
	}
}
