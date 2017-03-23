package org.dsikkema.orm.orm.sample.entity;

import org.dsikkema.orm.orm.entity.BaseBuilder;
import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.BuilderInterface;
import org.dsikkema.orm.orm.entity.EntityDefinition;
import org.dsikkema.orm.orm.entity.property.PropertyData;

/**
 * TODO possibly treat PersonBuilder as a decorator for BaseBuilder: both "is" and "has" a genericized BaseBuilder<PersonEntity> in order
 * to add typed getters/setters while writing less boilerplate code
 */
public class PersonBuilder implements BuilderInterface<PersonEntity> {
	private BaseBuilder baseBuilder;

	private PersonBuilder(BaseBuilder baseBuilder) {
		this.baseBuilder = baseBuilder;
	}
	
	public void setAge(int age) {
		// base builder deals with all data types as strings and internally validates them against the data type in the entity's schema
		this.baseBuilder.setProperty("age", String.valueOf(age));
	}
	
	public void setName(String name) {
		this.baseBuilder.setProperty("name", name);
	}
	
	@Override
	public PersonEntity build() {
		return new PersonEntity(this.baseBuilder.buildBaseEntity());
	}

	@Override
	public void setId(Integer id) {
		this.baseBuilder.setId(id);
	}
	
	@Override
	public Integer getId() {
		return this.baseBuilder.getId();
	}

	@Override
	public void setProperty(String propertyName, String property) {
		this.baseBuilder.setProperty(propertyName, property);
		
	}

	@Override
	public PropertyData getProperty(String propertyName) {
		return this.baseBuilder.getProperty(propertyName);
	}
	
	public static class Factory {
		private org.dsikkema.orm.orm.entity.EntityDefinition.Factory definitionFactory;

		public Factory(EntityDefinition.Factory definitionFactory) {
			this.definitionFactory = definitionFactory;
		}
		
		public PersonBuilder create(String entityType) {
			return new PersonBuilder(new BaseBuilder(this.definitionFactory.create(entityType)));
		}
	}
}