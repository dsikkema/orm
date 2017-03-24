package org.dsikkema.orm.orm.sample.entity;

import java.util.Map;

import org.dsikkema.orm.orm.entity.BaseBuilder;
import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.EntityDefinition;
import org.dsikkema.orm.orm.entity.EntityInterface;
import org.dsikkema.orm.orm.entity.property.PropertyData;

public class PersonEntity extends BaseEntity{
	

	public PersonEntity(EntityDefinition definition, Map<String, PropertyData> data, Integer id) {
		super(definition, data, id);
	}

	
	public String getName() {
		return this.getProperty("name").getStringValue();
	}
	
	public int getAge() {
		return this.getProperty("age").getIntValue();
	}

	public static PersonBuilder builder() {
		// TODO: use "definition pool" instead of factory
		EntityDefinition.Factory definitionFactory = new EntityDefinition.Factory();
		return new PersonBuilder(definitionFactory.create("person"));
	}
}
