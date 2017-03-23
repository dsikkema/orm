# ORM

*Work in progress*: begun March 17, 2017. If anything is overengineered (e.g. the Abstract Factory that creates a Builder that creates instances of a POJO), it's because this is really just a conceptual exercise.

The purpose is to let your application declare entities via xml, and then access them through repositories and searchable collections. The Data Access Objects themselves should be immutable. The storage engine should be configurable and replaceable, e.g. you can choose between mongo, sql, etc or you could write your own adapter for a new kind of database.

## But why
I want to see what a flexible, general purpose ORM would look like if you tried to adhere to certain standards of design, such as making sure objects are fully ready for use once instantiated (no `configure()` or `init()` methods), heavily using dependency injection, and making sure objects are mostly immutable. 

## Tasks
### Definitely
 - escaping sql injection
 - if xml validation is kept, then add config.xsd to define config.xml schema, and validate before reading
 - extract DB connection details into another config file (also validate with .xsd)
 - ability to automatically generate SQL script to build the schema for the given entities
 - bake in references to other entity types (by their IDs) as a "first-class citizen" type (not just as an int). Support data integrity ("foreign key" references) on framework level rather than rely on SQL foreign key constraints alone (because we could use a NoSQL db for instance, or we could eliminate foreign keys to allow for sharding).
 - support many-to-many relationships between entities
 - build a non-mysql adapter to prove the flexibility of this approach
 - allow for truly empty (nulled) optional properties without default values
 - use the guice DI container to build dependencies and allow configurable mapping of interfaces

### Maybe
 - use reflection and annotations off of an Entity class, rather than xml, to define entity
 - provide a tool to generate java code for the boilerplate portions of entity, builder, repository classes
 - simplify retrieval of repository for an entity type using a pool

## Open questions

How many classes should you be required to write in order to work with a new type of entity? Type-safety and compilation safeguards area good reason why you should have to implement at least one: an entity class with getters to reflect the fields of the entity (e.g. `String getName()`), so you don't have to call `getField("field_name")` without a statically guaranteed return type. A similar argument applies to writing methods like `setName(String name)` directly for the builder, rather than client code having to call `setField("field_name", Object value)` etc. But there's value in letting the builder have clunky access so that an application using this framework has one fewer class to write. And *certainly* it is necessary to get around needing to write a new Repository class for each entity.