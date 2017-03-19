package org.dsikkema.orm.orm.client;

import java.util.Map;

import org.dsikkema.orm.orm.BaseEntity;
import org.dsikkema.orm.orm.EntityDefinition;

public class MyEntity extends BaseEntity {
	private MyEntity(EntityDefinition definition, Map<String, Object> data) {
		super(definition, data);
	}
	
	public static class Builder extends BaseEntity.Builder {
		Builder(EntityDefinition definition) {
			super(definition);
		}
		
		public MyEntity build() {
			return new MyEntity(this.definition, this.data);
		}
		
		public static class Factory {
			private org.dsikkema.orm.orm.EntityDefinition.Factory definitionFactory;

			public Factory(EntityDefinition.Factory definitionFactory) {
				this.definitionFactory = definitionFactory;
			}
			
			public Builder create(String entityType) {
				return new Builder(this.definitionFactory.create(entityType));
			}
		}
	}
}
