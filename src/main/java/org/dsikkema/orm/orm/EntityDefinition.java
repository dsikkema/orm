package org.dsikkema.orm.orm;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EntityDefinition {
	private Map<String, PropertyDefinition> propertyDefinitions;
	private String entityType;
	
	private EntityDefinition(String entityType) {
		//scan xml file for info on this entity type, store info in this object
		this.entityType = entityType;
	}

	public String getEntityType() {
		return entityType;
	}
	
	public Map<String, PropertyDefinition> getPropertyDefinitions() {
		return Collections.unmodifiableMap(this.propertyDefinitions);
	}
	
	public boolean containsProperty(String property) {
		return this.propertyDefinitions.containsKey(property);
	}
	
	public PropertyDefinition getPropertyDefinition(String property) {
		this.verifyPropertyExists(property);
		return this.propertyDefinitions.get(property);
	}
	
	public boolean isPropertyRequired(String property) {
		this.verifyPropertyExists(property);
		return this.propertyDefinitions.get(property).isRequired();
	}
	
	public static class Factory {
		public EntityDefinition create(String entityType) {
			return new EntityDefinition(entityType);
		}
	}
	
	private void verifyPropertyExists(String property) {
		if (!this.containsProperty(property)) {
			throw new RuntimeException(String.format("Property '%s' is undefined for entity type '%s'", property, this.getEntityType()));
		}
	}
}
