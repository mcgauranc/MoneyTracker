# MoneyTracker

MoneyTracker is intended to be a simple, easy to use REST based money tracking application. It will facilitate both the tracking of transactions through a web page (currently working on the client side), and it will eventually facilitate the tracking of wallet expenditure, by means of a smart phone application. This application will also leverage OCR technology to read receipts and upload this to the server.

## Server

As mentioned above, the server side of MoneyTracker is implemented using REST. Following the REST principles, it is also stateless. Along with using the HTTP verbs GET, PUT, POST and DELETE to update information.

### REST

The REST functionality was achieved by leveraging [Spring Data Rest](http://www.springsource.org/spring-data/rest). It facilitates

* GET - Retrieval of required information, rendered in JSON format.
* PUT - Updating of information for a given entity, a JSON payload is required.
* POST - Inserting of information, a JSON payload is again, required.
* DELETE - Deleting of required information.

It also allows for JSONP and JSONP+E.

### Security

Most of the requests to the server need to be validated. Currently Base64 and Digest authorisation are catered for. However, only Base64 encoding of the username and password is provided for (used for testing purposes).
Most requests need to have an associated **Authorization** header. The only request that requires no authentication is the POST to the Users table. In all other instances only authorised, admin users, or users who own the relevant records
will be able to update them.

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

