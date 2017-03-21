package org.dsikkema.orm.orm.client.entity;

import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.BuilderFactory;
import org.dsikkema.orm.orm.entity.EntityDefinition;

public class Factory extends BuilderFactory {
	public Factory(EntityDefinition.Factory definitionFactory) {
		super(definitionFactory);
	}
	
	public Builder create(String entityType) {
		return new Builder(this.definitionFactory.create(entityType));
	}
}
