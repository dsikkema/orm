package org.dsikkema.orm.orm.entity;

import java.util.HashMap;
import java.util.Map;

import org.dsikkema.orm.orm.entity.property.PropertyData;
import org.dsikkema.orm.orm.entity.property.PropertyDefinition;

abstract public class BaseEntity {
	private final Map<String, PropertyData> data;
	private EntityDefinition definition;
	private Integer id;
	
	protected BaseEntity(EntityDefinition definition, Map<String, PropertyData> data, Integer id) {
		this.definition = definition;
		for (PropertyDefinition property : this.definition.getPropertyDefinitions().values()) {
			if (property.isRequired() && !data.containsKey(property.getName())) {
				throw new RuntimeException("Required property " + property.getName() + " is missing");
			}
		}
		this.data = data;
	}

	public PropertyData getProperty(String property) {
		if (!this.definition.containsProperty(property)) {
			throw new RuntimeException(String.format("Property '%s' does not exist for entity '%s'", property, this.definition.getEntityType()));
		}
		return this.data.get(property);
	}
	
	public Integer getId() {
		return this.id;
	}
	
	void setId(Integer id) {
		this.id = id;
	}
}
