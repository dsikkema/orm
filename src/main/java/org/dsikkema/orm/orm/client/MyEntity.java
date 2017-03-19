package org.dsikkema.orm.orm.client;

import java.util.Map;

import org.dsikkema.orm.orm.BaseEntity;
import org.dsikkema.orm.orm.EntityDefinition;
import org.dsikkema.orm.orm.PropertyData;

public class MyEntity extends BaseEntity {
	private MyEntity(EntityDefinition definition, Map<String, PropertyData> data) {
		super(definition, data);
	}
	
	public static class Builder extends BaseEntity.Builder {
		Builder(EntityDefinition definition) {
			super(definition);
		}
		
		public MyEntity build() {
			return new MyEntity(this.definition, this.data);
		}
		
		public static class Factory extends BaseEntity.Builder.Factory {
			private org.dsikkema.orm.orm.EntityDefinition.Factory definitionFactory;

			public Factory(EntityDefinition.Factory definitionFactory) {
				super(definitionFactory);
			}
			
			public Builder create(String entityType) {
				return new Builder(this.definitionFactory.create(entityType));
			}
		}
	}
}
