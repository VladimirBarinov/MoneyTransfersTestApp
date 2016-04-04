# MoneyTransfersTestApp
Simple money transfers application.It contains RESTful API for money transfers between users using
Dropwizard framework (it contains Jetty, Jersey, Jackson, Guava, JDBI etc. (http://www.dropwizard.io/0.9.2/docs/getting-started.html)) 
and H2 database (for keeping data in memory).
Created REST-services:

    GET     /accounts - get all accounts
    POST    /accounts/create - create new accounr 
    GET     /accounts/{accountId}  - get account by id
    DELETE  /accounts/{accountId}/delete - delete account by id
    PUT     /accounts/{accountId}/update - modify account by id
    GET     /currencies - get all currencies
    POST    /currencies/create - create new currency
    GET     /currencies/{currencyId} - get currency by id
    DELETE  /currencies/{currencyId}/delete - delete currency by id
    PUT     /currencies/{currencyId}/update - modify currency by id
    GET     /persons - get all persons
    POST    /persons/create - create new person
    GET     /persons/{personId} - get person by id
    DELETE  /persons/{personId}/delete - delete person by id
    PUT     /persons/{personId}/update - modify person by id
    GET     /transactions - get all transactions
    GET     /transactions/{transactionId} - get transaction by id
    PUT     /transactions/{transactionId}/update - modify transaction state
    POST    /transfers/create - create money transfer between 2 Persons


