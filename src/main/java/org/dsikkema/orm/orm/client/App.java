package org.dsikkema.orm.orm.client;

import org.dsikkema.orm.orm.client.entity.Builder;
import org.dsikkema.orm.orm.client.entity.Factory;
import org.dsikkema.orm.orm.client.entity.MyEntity;
import org.dsikkema.orm.orm.client.entity.Repository;
import org.dsikkema.orm.orm.db.DbConnection;
import org.dsikkema.orm.orm.entity.BuilderFactory;
import org.dsikkema.orm.orm.entity.EntityDefinition;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	Factory builderFactory = new Factory(new EntityDefinition.Factory());
    	Builder builder = builderFactory.create("person");
        Repository repo = new Repository(new DbConnection(), new EntityDefinition.Factory(), builderFactory);
//        
//        // make entity
//        builder.setProperty("name", "john");
//        builder.setProperty("age", "12");
//        
//        // save it
//        repo.create(builder);
        
//         load
        MyEntity person = repo.load(14);
        System.out.println(person.getProperty("name").getStringValue());
        System.out.println(person.getProperty("age").getIntValue());
    }
}
