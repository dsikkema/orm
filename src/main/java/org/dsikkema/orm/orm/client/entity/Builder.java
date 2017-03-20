package org.dsikkema.orm.orm.client.entity;

import org.dsikkema.orm.orm.entity.BaseBuilder;
import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.EntityDefinition;

public class Builder extends BaseBuilder {
	Builder(EntityDefinition definition) {
		super(definition);
	}
	
	public MyEntity build() {
		return new MyEntity(this.definition, this.data, this.id);
	}
}