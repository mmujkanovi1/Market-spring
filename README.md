# Advertising application #

Spring Boot sample app for advertising with five entities that are located in postgreSQL 
database. This app contains CRUD (create, read, update, delete) routes for "Users", 
"Advertisements", "Category" and "AppConfig". You can also run it in dev, prod and test
 environment. When the application is started in test environment, integration tests 
 that use h2 memory database are run. With JaCoCo we secured that the application 
 fails when code coverage is less than 85% on package level. User that is not logged 
 in can access just "/login"(where user can login) route and "/users POST" (where user 
 can register) route. I provided this with Json Web Token which contains "username",
  "id", and "role" of the user. JWT is implement in a way that every route except above 
  must have token in request headers, with restrictions that ensure that "Category" can
   create and update just the user with 'ADMIN' role; "Advertisements" can create and 
   update just by the user with 'USER' role, and "appConfig" CRUD routes can be 
   accessed just by 'ADMIN' users. Routes "/users" GET and "/advertisements" GET 
   have support for pagination and sorting where default values are located in 
   "appConfig" entity.


### Requirements! ###


* Installed java 11 on your machine. 
* Installed PostgresSQL on your machine and created database with name “olivebh” on port “2020” with credentials:
   * username: "postgres"
   * password: "sipassword2021"

### How to install and run application? ###
1. Download jar file from the repository
2. Open cmd as administrator
3. Type: 
```bash
java -jar /path/to/jar/file.jar
```

#### How to run application on dev enviroment? ####
* Type in cmd: 
```bash
java -jar -Dspring.profiles.active=dev /path/to/jar/file.jar
```

#### How to run application on prod enviroment? ####
* Type in cmd:
```bash
java -jar -Dspring.profiles.active=prod /path/to/jar/file.jar
```

#### How to run application on test enviroment? ####

Type in cmd:

```bash
java -jar -Dspring.profiles.active=test /path/to/jar/file.jar
```

### Where to find log file? ###
* In folder "logs" with name "spring.logs"

### REST API 
The REST API to the app is described below.

#### Login user


##### Request:

```
POST /login
```

Request body:

```
{
  "password": "string",
  "username": "string"
}
```

##### Response:

Response body:
```
{
  "token": "string"
}
```

#### Get list of Users

##### Request: 

Request header:
"Authorization" : "Bearer " + JWT
```
GET /users/
```


##### Response:


Response body:
```
[
  {
    "name": "string",
    "Surname": "string",
    "phoneNumber": "string",
    "email": "string",
    "id": 53
  }
]
```

#### Create user:


##### Request:

```
POST /users/
```

Request body:

```
{
  "Surname": "string",
  "email": "string",
  "name": "string",
  "phoneNumber": "string"
}
```

##### Response:

Response body:
```
{
  "id": 0
}
```

#### Get user with id:

##### Request:

Request header:
"Authorization" : "Bearer " + JWT

```
GET /users/id
```
Parameter:
```
id
```

##### Response:


Response body:
```
{
  "Surname": "string",
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string"
}
```

#### Update user:

##### Request:

Request header:
"Authorization" : "Bearer " + JWT

```
PUT /users/id
```
Parameter:
```
id
```

Request body:

```
{
  "Surname": "string",
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string"
}
```

##### Response:

Response body:
```
{
  "Surname": "string",
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string"
}
```

#### Delete user:

Request header:
"Authorization" : "Bearer " + JWT


```
DELETE /users/id
```
Parameter: 
```
id
```


##### Response:


Response body:
```
{
  "Surname": "string",
  "email": "string",
  "id": 0,
  "name": "string",
  "phoneNumber": "string"
}
```

#### Get Advertisements:

Request header:
"Authorization" : "Bearer " + JWT
 
 
```
GET /advertisements
```


##### Response:

Response body:
```
[
  {
    "description": "string",
    "id": 0,
    "title": "string"
  }
]
```

#### Get advertisement with id:
 
 
```
GET /advertisements/id
```
Parameter:
```
id
```


##### Response:

Response body:
```
{
  "description": "string",
  "id": 0,
  "title": "string"
}
```

#### Create advertisement:

Request header:
"Authorization" : "Bearer " + JWT


```
POST /advertisements/
```

Request body:

```
{
  "description": "string",
  "title": "string"
}
```

##### Response:


Response body:
```
{
  "id": 0
}
```

#### Update advertisement:

Request header:
"Authorization" : "Bearer " + JWT

 
```
PUT /advertisements/id
```
Parameter:
```
id
```

Request body:

```
{
  "description": "string",
  "id": 0,
  "title": "string"
}
```

##### Response:

Response body:
```
{
  "description": "string",
  "id": 0,
  "title": "string"
}
```

#### Delete advertisement:

Request header:
"Authorization" : "Bearer " + JWT

```
DELETE /advertisements/id
```
Parameter:
```
id
```

##### Response:

Response body:
```
{
  "description": "string",
  "id": 0,
  "title": "string"
}
```

#### Get Categories:

Request header:
"Authorization" : "Bearer " + JWT
 
 
```
GET /categories
```


##### Response:

Response body:
```
[
  {
    "categoryName": "string",
    "id": 0
  }
]
```

#### Get category with id:

Request header:
"Authorization" : "Bearer " + JWT
 
 
```
GET /categories/id
```
Parameter:
```
id
```


##### Response:

Response body:
```
{
  "categoryName": "string",
  "id": 0
}
```

#### Create category:

Request header:
"Authorization" : "Bearer " + JWT


```
POST /categories/
```

Request body:

```
{
  "categoryName": "string",
  "userId": 0
}
```

##### Response:


Response body:
```
{
  "id": 0
}
```

#### Update category:

Request header:
"Authorization" : "Bearer " + JWT

 
```
PUT /categories/id
```
Parameter:
```
id
```

Request body:

```
{
  "description": "string",
  "id": 0,
  "title": "string"
}
```

##### Response:

Response body:
```
{
  "categoryName": "string",
  "id": 0,
  "userId": 0
}
```

#### Delete category:

Request header:
"Authorization" : "Bearer " + JWT

```
DELETE /categories/id
```
Parameter:
```
id
```

##### Response:

Response body:
```
{
  "categoryName": "string",
  "id": 0
}
```


#### Get appConfig:

Request header:
"Authorization" : "Bearer " + JWT
 
 
```
GET /config
```


##### Response:

Response body:
```
{
  "advertisementPageDefaultSortField": "string",
  "advertisementPageItemsNo": 0,
  "id": 0,
  "userId": 0,
  "userPageDefaultSortField": "string",
  "userPageItemsNo": 0
}
```

#### Create appConfig:

Request header:
"Authorization" : "Bearer " + JWT


```
POST /config
```

Request body:

```
{
  "advertisementPageDefaultSortField": "string",
  "advertisementPageItemsNo": 0,
  "userPageDefaultSortField": "string",
  "userPageItemsNo": 0
}
```

##### Response:


Response body:
```
{
  "id": 0
}
```

#### Update appConfig:

Request header:
"Authorization" : "Bearer " + JWT

 
```
PUT /config
```

Request body:

```
{
  "advertisementPageDefaultSortField": "string",
  "advertisementPageItemsNo": 0,
  "userPageDefaultSortField": "string",
  "userPageItemsNo": 0
}
```

##### Response:

Response body:
```
{
  "advertisementPageDefaultSortField": "string",
  "advertisementPageItemsNo": 0,
  "id": 0,
  "userId": 0,
  "userPageDefaultSortField": "string",
  "userPageItemsNo": 0
}
```

#### Delete appConfig:

Request header:
"Authorization" : "Bearer " + JWT

```
DELETE /config
```

##### Response:

Response body:
```
{
  "advertisementPageDefaultSortField": "string",
  "advertisementPageItemsNo": 0,
  "id": 0,
  "userId": 0,
  "userPageDefaultSortField": "string",
  "userPageItemsNo": 0
}
```




