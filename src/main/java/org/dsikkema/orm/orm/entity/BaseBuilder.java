package org.dsikkema.orm.orm.entity;

import java.util.HashMap;
import java.util.Map;

import org.dsikkema.orm.orm.entity.property.PropertyData;
import org.dsikkema.orm.orm.entity.property.PropertyDefinition;

abstract public class BaseBuilder {
	protected final EntityDefinition definition;
	protected final Map<String, PropertyData> data;
	protected Integer id; 

	protected BaseBuilder(EntityDefinition definition) {
		this.definition = definition;
		this.data = new HashMap<String, PropertyData>();
		for (PropertyDefinition property : definition.getPropertyDefinitions().values()) {
			this.setProperty(property.getName(), property.getDefaultValue());
		}
	}
	
	public void setProperty(String property, String val) {
		this.setProperty(property, new PropertyData(this.definition.getPropertyDefinition(property), val));
	}
	
	public void setProperty(String property, PropertyData val) {
		this.verifyPropertyDefined(property);
		this.data.put(property, val);
	}
	
	public PropertyData getProperty(String property) {
		this.verifyPropertyDefined(property);
		return this.data.get(property);
	}

	public void setProperties(Map<String, String> data) {
		// iterate over, call setProperty
		for (Map.Entry<String, String> entry : data.entrySet()) {
			this.setProperty(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * For update, you will probably hydrate builder with e1, then override
	 * properties based on user input with setProperties, then build e2
	 * and finally save e2
	 */
	public void hydrate(BaseEntity entity) {
		// iterate over definition.getPropertyDefinitions
		for (String property : this.definition.getPropertyDefinitions().keySet()) {
			this.setProperty(property, entity.getProperty(property));
		}
		// for each, get from entity and set property to this
	}
	
	private void verifyPropertyDefined(String property) {
		if (!this.definition.containsProperty(property)) {
			throw new RuntimeException(String.format("Property '%s' is undefined for entity '%s'", property, this.definition.getEntityType()));
		}
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId(Integer id) {
		return this.id;
	}
	
	abstract public BaseEntity build();
}
