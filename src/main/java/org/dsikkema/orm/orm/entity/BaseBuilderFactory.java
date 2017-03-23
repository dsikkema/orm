package org.dsikkema.orm.orm.entity;

public class BaseBuilderFactory {
	protected org.dsikkema.orm.orm.entity.EntityDefinition.Factory definitionFactory;

	public BaseBuilderFactory(EntityDefinition.Factory definitionFactory) {
		this.definitionFactory = definitionFactory;
	}
	
	public BaseBuilder create(String entityType) {
		return new BaseBuilder(this.definitionFactory.create(entityType));
	}
}