package org.dsikkema.orm.orm.sample;

import org.dsikkema.orm.orm.db.DbConnection;
import org.dsikkema.orm.orm.entity.BaseRepository;
import org.dsikkema.orm.orm.entity.EntityDefinition;
import org.dsikkema.orm.orm.sample.entity.PersonBuilder;
import org.dsikkema.orm.orm.sample.entity.PersonEntity;

/**
 * Hello world!
 *
 */
public class Sample 
{
    public static void main( String[] args )
    {
    	// will be hidden behind DI once integrated with Guice
    	EntityDefinition.Factory entityDefinitionFactory = new EntityDefinition.Factory();
    	BaseRepository.Factory<PersonEntity> repositoryFactory = new BaseRepository.Factory<PersonEntity>(
			new DbConnection(),
			entityDefinitionFactory
		);
    	
    	// this will still be created by client code
    	PersonBuilder builder = PersonEntity.builder();
        BaseRepository<PersonEntity> repo = repositoryFactory.create("person");    	// TODO get this out of a RepositoryPool
        
        /**
         * replace id value with actually existing values from the db:
         * 
         * select entity_id from person;
         */
        
        int id = 24;
    	Sample.load(builder, repo, id);
    	Sample.create(builder, repo);
    }

	private static void load(PersonBuilder builder, BaseRepository<PersonEntity> repo, int id) {
		// load
        PersonEntity person = repo.load(builder, id);
        System.out.println(person.getName());
        System.out.println(person.getAge());
	}
    
    public static void create(PersonBuilder builder, BaseRepository<PersonEntity> repo) {
        // make entity
        builder.setName("john");
        builder.setAge(50);
        
        // save it
        repo.create(builder);
    }
}
