package org.dsikkema.orm.orm.entity.property;

public enum Type {
    INT("Int"),
    STRING("String");
	// TODO decimal
    private final String name;
	
	private Type(String n) {
		this.name = n;
	}
	
	public String getName() {
		return this.name;
	}
}
