package org.dsikkema.orm.orm.client.entity;

import java.util.Map;

import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.EntityDefinition;
import org.dsikkema.orm.orm.entity.property.PropertyData;

public class MyEntity extends BaseEntity {
	MyEntity(EntityDefinition definition, Map<String, PropertyData> data, Integer id) {
		super(definition, data, id);
	}
}
