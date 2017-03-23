package org.dsikkema.orm.orm.sample.entity;

import java.util.Map;

import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.EntityDefinition;
import org.dsikkema.orm.orm.entity.EntityInterface;
import org.dsikkema.orm.orm.entity.property.PropertyData;

public class PersonEntity implements EntityInterface {
	private BaseEntity baseEntity;

	public PersonEntity(BaseEntity baseEntity) {
		this.baseEntity = baseEntity;
	}
	
	public String getName() {
		return this.baseEntity.getProperty("name").getStringValue();
	}
	
	public int getAge() {
		return this.baseEntity.getProperty("age").getIntValue();
	}
}
