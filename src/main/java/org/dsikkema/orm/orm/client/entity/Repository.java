package org.dsikkema.orm.orm.client.entity;

import java.sql.ResultSet;

import org.dsikkema.orm.orm.db.DbConnection;
import org.dsikkema.orm.orm.entity.BaseBuilder;
import org.dsikkema.orm.orm.entity.BaseEntity;
import org.dsikkema.orm.orm.entity.BaseRepository;
import org.dsikkema.orm.orm.entity.BuilderFactory;
import org.dsikkema.orm.orm.entity.EntityDefinition;
import org.dsikkema.orm.orm.entity.property.PropertyData;
import org.dsikkema.orm.orm.entity.property.PropertyDefinition;

public class Repository extends BaseRepository {

	public Repository(DbConnection dbConn, EntityDefinition.Factory definition, BuilderFactory builderFactory) {
		super(dbConn, definition.create("person"), builderFactory);
	}
	
    public MyEntity load(Integer id) { 
        return (MyEntity)super.load(id);
    }
    
    /**
     * @return Boolean whether entity was loaded successfully
     */
    public void delete(Integer id) { 
        super.delete(id);
    }
    
    /**
     * you create id and return entity
     */
    public MyEntity create(BaseBuilder builder) {
        return (MyEntity)super.create(builder);
    }
    
    /**
     * you don't update id but you return entity
     */
    public MyEntity update(Integer id, BaseBuilder builder) {
        return (MyEntity)super.update(id, builder);
    }
}
