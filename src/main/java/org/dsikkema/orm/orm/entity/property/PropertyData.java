package org.dsikkema.orm.orm.entity.property;

public class PropertyData {

	private PropertyDefinition definition;
	private Object val;

	public PropertyData(PropertyDefinition definition, String val) {
		switch (definition.getType()) { 
			case INT:
				if (!this.isInt(val)) {
					throw new RuntimeException(String.format(
		    			"Property '%s' with value '%s' does not match expected type '%s'",
		    			definition.getName(),
		    			val,
		    			definition.getType().getName()
					));
				}
				this.val = Integer.valueOf(val);
				break;
			case STRING:
				this.val = val;
				break;
				
			default:
				throw new RuntimeException("Unhandled property type"); // just to make this compile. Should never be hit
		}
		
		this.definition = definition;
	}

	public String getStringValue() {
		if (this.definition.getType() != Type.STRING) {
			throw new RuntimeException("Property '" + definition.getName() + "' is not of type " + Type.STRING.getName());
		}
	
		return (String)this.val;
	}
	
	public int getIntValue() {
		if (this.definition.getType() != Type.INT) {
			throw new RuntimeException("Property '" + definition.getName() + "' is not of type " + Type.INT.getName());
		}
	
		return (int)this.val;
	}
	
	private boolean isInt(String in) {
	    try {
	        Integer.parseInt(in);
	    } catch (NumberFormatException e) {
	        return false;
	    }
	    return true;
	}
}
