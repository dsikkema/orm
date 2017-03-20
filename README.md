# ORM

Work in progress: begun March 17, 2017. If anything looks "excessive" (e.g. the Abstract Factory that creates a Builder that creates an instances of an abstract class), it's because this is really just a conceptual exercise.

The purpose is to let your application declare entities via xml, and then access them through repositories and searchable collections. The Data Access Objects themselves should be immutable. The storage engine should be configurable and replaceable, e.g. you can choose between mongo, sql, etc or you could write your own adapter for a new kind of database.

# TODO
 - make basic saving/loading/reading entities work
 - xml validation for config.xml
 - extract DB connection details into another config file (validated also)
 - ability to write SQL script to build the schema for the given entities
 - bake in references to other entity types (by their IDs) as a "first-class citizen" type (not just as an int). Support data integrity on framework level rather than rely on foreign key constraints alone (because we could use a NoSQL db for instance, or we could eliminate foreign keys to allow for sharding).
 - support many-to-many relationships between entities
 - build a non-mysql adapter to prove the flexibility
 - allow for truly empty (nulled) optional properties without default values
