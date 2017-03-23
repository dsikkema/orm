package org.dsikkema.orm.orm.entity;

import org.dsikkema.orm.orm.entity.property.PropertyData;

public interface BuilderInterface<T> {
	public void setId(Integer id);
	public Integer getId();
	public T build();
	public void setProperty(String propertyName, String property);
	public PropertyData getProperty(String propertyName);
}
