package org.dsikkema.orm.orm.sample;

import org.dsikkema.orm.orm.db.DbConnection;
import org.dsikkema.orm.orm.entity.BaseRepository;
import org.dsikkema.orm.orm.entity.BaseBuilderFactory;
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
    	// will be hidden behind DI...
    	EntityDefinition.Factory entityDefinitionFactory = new EntityDefinition.Factory();
    	PersonBuilder.Factory personBuilderFactory = new PersonBuilder.Factory(entityDefinitionFactory);
    	BaseRepository.Factory<PersonEntity> repositoryFactory = new BaseRepository.Factory<PersonEntity>(
			new DbConnection(),
			entityDefinitionFactory,
			new BaseBuilderFactory(entityDefinitionFactory)
		);
    	
    	// created manually...
    	PersonBuilder builder = personBuilderFactory.create("person");
    	// could get this out of a RepositoryPool
        BaseRepository<PersonEntity> repo = repositoryFactory.create("person", PersonEntity.class);

        
    	Sample.load(builder, repo);
    	Sample.create(builder, repo);
    }

	private static void load(PersonBuilder builder, BaseRepository<PersonEntity> repo) {
		// load
        PersonEntity person = repo.load(builder, 14);
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
