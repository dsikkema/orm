package org.dsikkema.orm.orm.entity;

import java.sql.ResultSet;

import org.dsikkema.orm.orm.db.DbConnection;
import org.dsikkema.orm.orm.entity.property.PropertyDefinition;

public class BaseRepository {
	private DbConnection dbConn;
	private Integer id = null;
	private EntityDefinition definition;
	private BuilderFactory builderFactory;
	
	BaseRepository(
		DbConnection dbConn,
		EntityDefinition definition,
		BuilderFactory builderFactory
	) {
		this.dbConn = dbConn;
		this.definition = definition;
		this.builderFactory = builderFactory;
	}
	
	/**
     * @return Boolean whether entity was loaded successfully
     */
    public BaseEntity load(Integer id) { 
        String sql;
        BaseBuilder builder = this.builderFactory.create(this.definition.getEntityType());
        // TODO don't hardcode entity_id
        sql = "select * from " + this.definition.getEntityType() + " where entity_id=" + id + ";";
        
        try {
            ResultSet result = dbConn.doQuery(sql);
            if (result.next()) {
                for (PropertyDefinition property: this.definition.getPropertyDefinitions().values()) {
                    builder.setProperty(property.getName(), result.getString(property.getName()));
                }
                builder.setId(id);
            } else {
            	return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading entity", e);
        }
        return builder.build();
    }
    
    /**
     * @return Boolean whether entity was loaded successfully
     */
    public void delete(Integer id) { 
        String sql;
        sql = "delete from " + this.definition.getEntityType() + " where entity_id=" + id + ";";
        
        try {
            dbConn.doUpdateQuery(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entity", e);
        }
    }
    
    /**
     * you create id and return entity
     */
    public BaseEntity create(BaseBuilder builder) {
        String sql;
        String values = "";
        String fields = "";
        BaseEntity entity;
        
    	for (String property: this.definition.getPropertyDefinitions().keySet()) {
    		values += builder.getProperty(property) + "','";
    		fields += property + ",";
    	}
    	
    	values = "'" + values.substring(0, values.length() - 2); // remove trailing comma, quotation
    	fields = fields.substring(0, values.length() - 1);
        sql = "INSERT INTO " + this.definition.getEntityType() + " (" + fields + ") VALUES (" + values + ");";
        
        try {
            dbConn.doUpdateQuery(sql);
	        ResultSet result = dbConn.doQuery("SELECT LAST_INSERT_ID()");
	        if (result.next()) {
	        	entity = builder.build();
	        	builder.setId(result.getInt(1));
	        } else {
	        	// cannot retrieve ID of saved entity
	        	throw new RuntimeException("Cannot retrieve ID of saved entity");
	        }
        } catch (Exception e) {
        	throw new RuntimeException("Error saving entity", e);
        }
        
        return entity;
    }
    
    /**
     * you don't update id but you return entity
     */
    public BaseEntity update(Integer id, BaseBuilder builder) {
        String sql;
        String updateString = "";
        BaseEntity entity = builder.build();
        
    	for (String property: this.definition.getPropertyDefinitions().keySet()) {
    		updateString += property + "='" + entity.getProperty(property) + "',";
    	}
    	updateString = updateString.substring(0, updateString.length() - 1); // remove trailing comma
        sql = "UPDATE " + this.definition.getEntityType() + " SET " + updateString + " WHERE entity_id='" + entity.getId() + "';";
        
        try {
            dbConn.doUpdateQuery(sql);
        } catch (Exception e) {
        	throw new RuntimeException("Error saving entity", e);
        }
        return entity;
    }
}
