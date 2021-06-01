# Setter Getter Generator

A tool written in java version 8 to provide ease to developers by writing setter getter methods from available database relations.
The prime benefit of this tool is , it saves much time and developer efforts by writing pojo's for hundred's of DB relation attributes.
This kind of tools are heavily used in big scaled Frameworks & IDE's like - STS (Spring Tool Suite).

## Features

* It generates pojo's for Database Tables.
* It can generate specific setter getter's for mentioned class properties by customizing on your own.
* It is easily customizable and can be used accordingly.
* Reflection API is used because of which it can easily work for any database like - Java Derby , My SQL & Microsoft SQL Server.
* File Handling is used to create separate files for each setter getter.

## Getting Started

### Dependencies

* You need to add specific database jar files accordingly in your classpath while compiling & executing.
* Java Version 8 is preferred.
* In case of NO SQL Databases , you need to customise it in context to meta data's and result set's.

### Testing

* This Tool is tested on MySQL Server & Java Derby.

### Executing 

* Create a folder named as tools on C:\ root directory.
* Paste the linked java file.
* Create a folder named as libs and paste the necessary jar files required for database connections.
* To compile & execute the tool , write the following statement on CLI -
```
javac -classpath c:\<directory_name>\libs\*;. Tool.java
java -classpath c:\<directory_name>\libs\*;. Tool
```

## Help

Try to debug meta data & result set data structures by iterating to keep track of fetched fields.
