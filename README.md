# MoneyTracker

MoneyTracker is intended to be a simple, easy to use REST based money tracking application. It will facilitate both the tracking of transactions through a web page, and it will eventually facilitate the tracking of wallet expenditure, by means of a smart phone application. 
This application will also leverage OCR technology to read receipts and upload this to the server.

## Server

As mentioned above, the server side of MoneyTracker is implemented using REST. Following the REST principles, it is also stateless. Along with using the HTTP verbs GET, PUT, POST and DELETE to update information. 
It uses spring-boot to setup the server webapp, along with hibernate, which deals with the database access. The REST server is exposed using spring-data-rest.

### Setup

The best way to run this application would be to use maven. Please ensure that you have downloaded it, and have set the path accordingly. The server uses [Spring Boot] (http://projects.spring.io/spring-boot/) 
to launch the spring-data-rest application, which means that it doesn't require an external Tomcat to get it running. All of the application configuration is done in the
*application.yml* file, in the resources directory of the server. To get the server running, do the following:

1. Create the MoneyTrak database in SQL Server.
2. Set the database user name and password for database access.
3. Run "mvn spring-boot:run" in the command line for the server project.
4. Access <http://localhost:8080/#/>
5. Enter in "Admin" and "Passw0rd" as the username and password to access this Url. These are the default credentials, which are automatically created.

Once the server is up and running you can run <http://localhost:8080/health> to ensure that everything is running correctly. See [Endpoints] (http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#production-ready) for
a list of endpoints that can be used.
 
*NOTE: All steps above, presume a default implementation. i.e. That you've made no changes to the application.yml, other than to the username and password to access the database.*

### REST

The REST functionality was achieved by leveraging [Spring Data Rest](http://www.springsource.org/spring-data/rest). It facilitates

* GET - Retrieval of required information, rendered in JSON format.
* PUT - Updating of information for a given entity, a JSON payload is required.
* POST - Inserting of information, a JSON payload is again, required.
* DELETE - Deleting of required information.

It also allows for JSONP and JSONP+E.

Access to the REST API is provided by HATEOAS, if you navigate to <http://localhost:8080/api> you'll see a list of available URLs

### Security

Most of the requests to the server need to be validated. Only Base64 encoding of the username and password is provided for (used for testing purposes).
Most requests need to have an associated **Authorization** header. The only request that requires no authentication is the POST to the Users table. In all other instances only authorised, admin users, or users who own the relevant records
will be able to access/update them.

### Data Layer

The data layer is currently implemented using [Hibernate](http://www.hibernate.org/), which uses JPA to expose the tables to Spring Data Rest. The underlying database connection to the database uses C3P0. The following tables are created:

* Account
* Account_Audit
* AccountType
* Address
* Authorities
* Category
* Country
* Currency
* Group_Authorities
* Groups
* Payee
* Payee_Audit
* Revision
* Undertaking (Transaction)
* Undertaking_Audit
* Users
* Users_Account
* Users_Audit
* Users_Groups

### Auditing

All auditing is performed, using [Hibernate Envers](http://docs.jboss.org/envers/docs/) To ensure that changes are tracked, the following several tables are audited:

* Account
* Payee
* Undertaking (Transaction)
* Users

### Logging

All logging is taken care of using [Logback](http://logback.qos.ch/). It natively speaks to SLF4J, and allows for automatic reloading of configuration files.

## Client

The client side will currently use AngularJS in conjunction with Kendo UI. A single page approach, to displaying information will be taken. AngularJS facilitates an MVC approach to developing
 client side code - which fits in nicely with what I want to achieve with this project. It also leverages the great capabilities of Kendo UI. The following URLs will be useful should you wish to
  read up further.

  * [AngularJS](http://angularjs.org/)
  * [RestAngular] (https://github.com/mgonto/restangular)
