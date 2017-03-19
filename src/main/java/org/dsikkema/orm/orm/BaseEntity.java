package org.dsikkema.orm.orm;

import java.util.HashMap;
import java.util.Map;

import org.dsikkema.orm.orm.client.MyEntity.Builder;

abstract public class BaseEntity {
	private final Map<String, PropertyData> data;
	private EntityDefinition definition;

	protected BaseEntity(EntityDefinition definition, Map<String, PropertyData> data) {
		this.definition = definition;
		this.data = data;
	}

	public PropertyData getProperty(String property) {
		if (!this.definition.containsProperty(property)) {
			throw new RuntimeException(String.format("Property '%s' does not exist for entity '%s'", property, this.definition.getEntityType()));
		}
		return this.data.get(property);
	}
	
	abstract public static class Builder {
		protected final EntityDefinition definition;
		protected final Map<String, PropertyData> data;

		protected Builder(EntityDefinition definition) {
			this.definition = definition;
			this.data = new HashMap<String, PropertyData>();
		}
		
		public void setProperty(String property, String val) {
			this.setProperty(property, new PropertyData(this.definition.getPropertyDefinition(property), val));
		}
		
		public void setProperty(String property, PropertyData val) {
			this.verifyPropertyExists(property);
			this.data.put(property, val);
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
		
		private void verifyPropertyExists(String property) {
			if (!this.definition.containsProperty(property)) {
				throw new RuntimeException(String.format("Property '%s' is undefined for entity '%s'", property, this.definition.getEntityType()));
			}
		}
		
		abstract public BaseEntity build();
		
		abstract public static class Factory {
			private org.dsikkema.orm.orm.EntityDefinition.Factory definitionFactory;

			public Factory(EntityDefinition.Factory definitionFactory) {
				this.definitionFactory = definitionFactory;
			}
			
			abstract public Builder create(String entityType);
		}
	}
}
