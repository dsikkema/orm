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
public class PersonBuilder extends BaseBuilder<PersonEntity> {
	public PersonBuilder(EntityDefinition definition) {
		super(definition);
		assert definition.getEntityType() == "person";
	}

	public void setAge(int age) {
		// base builder deals with all data types as strings and internally validates them against the data type in the entity's schema
		this.setProperty("age", String.valueOf(age));
	}
	
	public void setName(String name) {
		this.setProperty("name", name);
	}
	
	public PersonEntity build() {
		return new PersonEntity(this.definition, this.data, this.id);
	}
}