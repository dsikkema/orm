package org.dsikkema.orm.orm.entity.property;

public class PropertyDefinition {
	
	private String name;
	private boolean isRequired;
	private Type type;
	private String defaultValue;

	public PropertyDefinition(String name, boolean isRequired, Type type, String defaultValue) {
		this.name = name;
		this.isRequired = isRequired;
		this.type = type;
		this.defaultValue = defaultValue;
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
	
	public String getDefaultValue() {
		return this.defaultValue;
	}
}
