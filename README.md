# MoneyTracker

MoneyTracker is intended to be a simple, easy to use REST based money tracking application. It will facilitate both the tracking of transactions through a web page (currently working on the client side), and it will eventually facilitate the tracking of wallet expenditure, by means of a smart phone application. This application will also leverage OCR technology to read receipts and upload this to the server.

## Server

As mentioned above, the server side of MoneyTracker is implemented using REST. Following on from the REST principles, it is also stateless. Along with using the HTTP verbs GET, PUT, POST and DELETE - all of which, are catered for.

### REST

The REST functionality was achieved by leveraging [Spring Data Rest](http://www.springsource.org/spring-data/rest). It facilitates

* GET - Retrieval of required information, rendered in JSON format.
* PUT - Updating of information for a given entity, a JSON payload is required.
* POST - Inserting of information, again a JSON payload is required.
* DELETE - Deleting of required information.

### Security

Most of the request to the server need to be validated. Currently Base64 and digest authorisation are facilitated for. However, only Base64 encoding of the username and password is catered for (for testing purposes only).
Most requests need to have an associated **Authorization** header. The only request that requires no authentication is the POST to the Users table. In most other instances only admin users, or users who own the relevant records
will be able to update them.

### Data Layer

The data layer is currently implemented using [Hibernate](http://www.hibernate.org/), which uses JPA to expose the tables to Spring Data Rest. The underlying database connection to the database uses C3P0. The following tables are created:

* Account
* AccountType
* Address
* Authorities
* Category
* Country
* Currency
* Groups
* Payee
* Undertaking (Transaction)
* Users

### Auditing

All auditing is performed, using [Hibernate Envers](http://docs.jboss.org/envers/docs/) To ensure that changes are tracked, several tables are audited. These include:

* Account
* Payee
* Transaction
* Users


## Client

The client side will currently just be a collection of JSP pages, which will use jQuery as the underlying functionality to access the relevant REST URI's exposed by the server.

# License

MoneyTracker is licensed under the New BSD license.

Copyright (c) 2013, MoneyTracker Contributors All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. Neither the name of MoneyTracker nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.