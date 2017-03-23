package org.dsikkema.orm.orm.entity;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;

import org.dsikkema.orm.orm.db.DbConnection;
import org.dsikkema.orm.orm.entity.property.PropertyData;
import org.dsikkema.orm.orm.entity.property.PropertyDefinition;

public class BaseRepository<T> {
	private DbConnection dbConn;
	private BaseBuilderFactory builderFactory;
	private EntityDefinition definition;
	
	private BaseRepository(
		DbConnection dbConn,
		BaseBuilderFactory builderFactory,
		EntityDefinition definition
	) {
		this.dbConn = dbConn;
		this.definition = definition;
		this.builderFactory = builderFactory;
	}
	
	/**
     * @return Boolean whether entity was loaded successfully
     * 
     * TODO make the reflection less ugly here
     */
    public T load(BuilderInterface<T> builder, Integer id) { 
        String sql;
        // TODO don't hardcode entity_id
        sql = "SELECT * FROM " + definition.getEntityType() + " WHERE entity_id=" + id + ";";
        
        try {
            ResultSet result = dbConn.doQuery(sql);
            if (result.next()) {
                for (PropertyDefinition property: definition.getPropertyDefinitions().values()) {
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
        sql = "delete from " + definition.getEntityType() + " where entity_id=" + id + ";";
        
        try {
            dbConn.doUpdateQuery(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entity", e);
        }
    }
    
    /**
     * you create id and return entity
     */
    public T create(BuilderInterface<T> builder) {
        String sql;
        String values = "";
        String fields = "";
        T entity;
        
    	for (String property: definition.getPropertyDefinitions().keySet()) {
    		values += this.getProperty(builder.getProperty(property)) + "','";
    		fields += property + ",";
    	}
    	
    	values = "'" + values.substring(0, values.length() - 2); // remove trailing comma, quotation
    	fields = fields.substring(0, fields.length() - 1);
        sql = "INSERT INTO " + definition.getEntityType() + " (" + fields + ") VALUES (" + values + ");";
        
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
    public T update(Integer id, BuilderInterface<T> builder) {
        String sql;
        String updateString = "";
        T entity = builder.build();
        
    	for (String property: definition.getPropertyDefinitions().keySet()) {
    		updateString += property + "='" + builder.getProperty(property) + "',";
    	}
    	updateString = updateString.substring(0, updateString.length() - 1); // remove trailing comma
        sql = "UPDATE " + definition.getEntityType() + " SET " + updateString + " WHERE entity_id='" + builder.getId() + "';";
        
        try {
            dbConn.doUpdateQuery(sql);
        } catch (Exception e) {
        	throw new RuntimeException("Error saving entity", e);
        }
        
        return entity;
    }
    
    private String getProperty(PropertyData property) {
    	switch (property.getType()) {
	    	case INT:
	    		return String.valueOf(property.getIntValue());
			default:
			case STRING:
				return property.getStringValue();
    	}
    }
    
    public static class Factory<T> {
    	private DbConnection dbConn;
    	private BaseBuilderFactory builderFactory;
		private EntityDefinition.Factory definitionFactory;
    	
    	public Factory(
    		DbConnection dbConn,
    		EntityDefinition.Factory definitionFactory,
    		BaseBuilderFactory builderFactory
    	) {
    		this.dbConn = dbConn;
			this.definitionFactory = definitionFactory;
    		this.builderFactory = builderFactory;
    	}
    	
    	public BaseRepository<T> create(String entityType) {
    		return new BaseRepository<T>(this.dbConn, this.builderFactory, this.definitionFactory.create(entityType));
    	}
    }
}
