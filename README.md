# ORM

*Work in progress*: begun March 17, 2017. If anything is overengineered (e.g. the Abstract Factory that creates a Builder that creates instances of a POJO), it's because this is really just a conceptual exercise.

The purpose is to let your application declare entities via xml, and then access them through repositories and searchable collections. The Data Access Objects themselves should be immutable. The storage engine should be configurable and replaceable, e.g. you can choose between mongo, sql, etc or you could write your own adapter for a new kind of database.

## But why
I want to see what a flexible, general purpose ORM would look like if you tried to adhere to certain standards of design, such as making sure objects are fully ready for use once instantiated (no `configure()` or `init()` methods), heavily using dependency injection, and making sure objects are mostly immutable. 

## How to use
Eventually I'll create a separate sample project that's easy to build with Maven and run from the terminal, so as not to mix the "framework" with the sample code, but right now, just load the project up in eclipse and run the "Sample" class.

To use in your application:
 - define an entity and its properties in src/main/resources/orm.xml (person.xml as an example)
 - make sure the DB schema exists for your entity. "entity_id" is required to be used as the primary key
 - add two classes for your entity: a builder class and a class that represents the actual entity
 	- Builder should extend BaseBuilder, see PersonBuilder as an example. It should have a build method that instantiates your Entity class and may have well-typed and well-named setters.
 	- Entity class should extend BaseEntity, see PersonEntity as an example. It should have a static builder() method that returns its dedicated builder, and may have well-typed and well-named getters for properties. It is immutable.
 - use the builder() method to retrieve the builder in your application
 - use BaseRepository.Factoy<YourEntityClass> to create an instance of a repository for your entity
 - You may then populate a builder with information however you please.
 - Use the repository to do CRUD work on your entity. Load and delete methods just take an ID, but create and update methods take a builder. 

## Tasks
### Definitely TODO
 - escaping sql injection
 - use "pools" of repositories and definition objects, rather than factories. Duplicate instances unnecessary.
 - if xml validation is kept, then add config.xsd to define config.xml schema, and validate before reading
 - extract DB connection details into another config file (also validate with .xsd)
 - ability to automatically generate SQL script to build the schema for the given entities
 - bake in references to other entity types (by their IDs) as a "first-class citizen" type (not just as an int). Support data integrity ("foreign key" references) on framework level rather than rely on SQL foreign key constraints alone (because we could use a NoSQL db for instance, or we could eliminate foreign keys to allow for sharding).
 - support many-to-many relationships between entities
 - build a non-mysql adapter to prove the flexibility of this approach
 - allow for truly empty (nulled) optional properties without default values
 - use the guice DI container to build dependencies and allow configurable mapping of interfaces

### Possibly TODO
 - use reflection and annotations off of an Entity class, rather than xml, to define entity
 - provide a tool to generate java code for the boilerplate portions of entity, builder, repository classes
