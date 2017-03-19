package org.dsikkema.orm.orm;

import java.sql.ResultSet;

import org.dsikkema.orm.orm.BaseEntity.Builder;
import org.dsikkema.orm.orm.db.DbConnection;

public class BaseRepository {
	private DbConnection dbConn;
	private Integer id = null;
	private EntityDefinition definition;
	private BaseEntity.Builder.Factory builderFactory;
	
	BaseRepository(
		DbConnection dbConn,
		EntityDefinition definition,
		BaseEntity.Builder.Factory builderFactory
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
        Builder builder = this.builderFactory.create(this.definition.getEntityType());
        // TODO don't hardcode entity_id
        sql = "select * from " + this.definition.getEntityType() + " where entity_id=" + id + ";";
        
        try {
            ResultSet result = dbConn.doQuery(sql);
            if (result.next()) {
                for (PropertyDefinition property: this.definition.getPropertyDefinitions().values()) {
                    builder.setProperty(property.getName(), result.getString(property.getName()));
                }
                builder.setId(id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error loading entity", e);
        }
        return builder.build();
    }
    
    /**
     * @return Boolean whether entity was loaded successfully
     */
    public Boolean delete() { 
        /**
         * if entity is new, there is no reason to believe it actually exists in DB
         */
        if (this.isNew()) {
            throw new RuntimeException("Cannot delete entity that is not loaded from or saved to database");
        }
        String sql;
        sql = "delete from " + this.tableName + " where " + this.idField + "=" + this.getId() + ";";
        
        try {
            dbConn.doUpdateQuery(sql);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting entity", e);
        }
        return !this.isNew(); 
    }
    
    /**
     * TODO: protect from SQL injection
     */
    public void save() {
        String sql;
        String value = "";
        String updateString = "";
        
        this.validateData();
        
        // Entity being saved for first time
        if (this.isNew()) {
        	for (String attribute : this.attributes) {
        		value += this.data.get(attribute) + "','";
        	}
        	value = "'" + value.substring(0, value.length() - 2); // remove trailing comma 
            sql = "INSERT INTO " + this.tableName + " (" + String.join(",", this.attributes) + ") VALUES (" + value + ");";
        // Entity being updated
        } else {
        	for (String attribute : this.attributes) {
        		updateString += attribute + "='" + this.data.get(attribute) + "',";
        	}
        	updateString = updateString.substring(0, updateString.length() - 1); // remove trailing comma
            sql = "UPDATE " + this.tableName + " SET " + updateString + " WHERE " + this.idField + "='" + this.getId() + "';";
        }
        
        try {
            dbConn.doUpdateQuery(sql);
            
            /**
             * If new entity then retrieve its auto-generated ID from DB and set it
             */
	        if (this.isNew()) {
		        ResultSet result = dbConn.doQuery("SELECT LAST_INSERT_ID()");
		        if (result.next()) {
		        	this.setId(result.getInt(1));
		        } else {
		        	// cannot retrieve ID of saved entity
		        	throw new RuntimeException("Cannot retrieve ID of saved entity");
		        }
	        }
        } catch (Exception e) {
        	throw new RuntimeException("Error saving entity", e);
        }
    }
}
