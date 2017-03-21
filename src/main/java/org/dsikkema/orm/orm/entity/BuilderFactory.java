package org.dsikkema.orm.orm.entity;

abstract public class BuilderFactory {
	protected org.dsikkema.orm.orm.entity.EntityDefinition.Factory definitionFactory;

	public BuilderFactory(EntityDefinition.Factory definitionFactory) {
		this.definitionFactory = definitionFactory;
	}
	
	abstract public BaseBuilder create(String entityType);
}