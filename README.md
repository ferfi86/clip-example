# Clip Challenge

## Prerequisites
- Spring Boot
- JPA
- H2
- Maven 4
- Java 11

## Description
This applications consist of the following:

- Controller package:  where the basic endpoint is.
- Repository package:  repository package for 
- Model package: where entities are stored.
- Request package: objects that represent request.


The project contains a simple API that saves a payment in an in-memory database (for the sake of this example lets use a in-memory-database).
The challenge consists of completing as many of the following steps as possible:

1. Create a new endpoint to list all users that have a payment saved in the database (information about payments should be already filled).
2. Create a new  endpoint so the api can support a disbursement process:
    - A disbursement process gets all transactions with status new and subtracts a fee of 3.5%  per transaction.
    - It updates all transactions with a status NEW  to PROCESSED.
    - Returns a list of users and the amount the'll get 
    -- Example - User_1 payment: 100, Disbursement: User_1:97.5 (Discount the fee)
3. Create an endpoint that returns a report per user:
    - Report:
    `{
      user_name - user name
      payments_sum: - sum of all payments (no mater what's the status)
      new_payments: sum of all new payments 
      new_payments_amount: sum of the amount of each payment
    }`
4. Add security for the disbursement process endpoint.


##Notes:
- The expected minimum is that you complete steps 1,2,3. 4 is optional  (Completing all is an extra).
- We want see your skills and abilities to code so if at any moment you want to change or refactor anything go ahead.
- We are considering as reviewers that your code challenge is code-prod-quality and it will review under this impression.
- Please initialize the directory with the challenge as a git repo so you can commit new features and we check on your thought process.
- Please upload the code-challenge to a git-repository and share the access with the reviewers thavt recruitment team indicates.

# Results API

 [Swagger API documentation](http://localhost:8080/swagger-ui.html#/).


## 1: **users**

```http
GET /api/clip/users
```

#### Response
List of all saved users

```javascript
[
    {
        "id": 1,
        "name": "User 1"
    },
    {
        "id": 2,
        "name": "User 2"
    },
]
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |


## 2: **Users with payments**

```http
GET /api/clip/usersWithPayments
```

#### Response
list of all users saved with assigned payments 

```javascript
[
    {
        "id": 1,
        "name": "User 1"
    },
    {
        "id": 2,
        "name": "User 2"
    },
]
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |


## 3: **User info**

```http
GET /api/clip/usersWithPayments/{userId}
```

```http
GET /api/clip/usersWithPayments/{userId}
```
#### Parameter
user Id

```http
2
```

#### Response
User information

```javascript
 {
        "id": 2,
        "name": "User 2"
 }
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 404 | `USER NOT FOUND` |


## 4: **Payments**

```http
GET /api/clip/payments
```

#### Response
List of payments saved

```javascript
 [
    {
        "id": 1,
        "amount": 100.00,
        "status": "NEW",
        "user": {
            "id": 4,
            "name": "Videl"
        }
    },
    {
        "id": 2,
        "amount": 100.00,
        "status": "NEW",
        "user": {
            "id": 1,
            "name": "Son Goku"
        }
    },
    {
        "id": 3,
        "amount": 292.50,
        "status": "PROCESSED",
        "user": {
            "id": 4,
            "name": "Videl"
        }
    }
]
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |

## 6: **Create payment**

```http
POST /api/clip/createPayload
```
#### Parameters
payment Request

```javascript
{
  "amount": 0,
  "userId": 0
}
```

#### Response
Payment created

```javascript
 {
    "id": 3,
    "amount": 300,
    "status": "NEW",
    "user": {
        "id": 4,
        "name": "Videl"
    }
}
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 201 | `CREATED` |
| 404 | `USER NOT FOUND` |

## 7: **Disbursement Process**

```http
POST /api/clip/disbursementProcess
```

### Authorization
Basic Auth : only users with **ADMIN** role can execute this service

| User | Password | Role |
| :--- | :--- | :--- |
| `admin` | `password` | `ADMIN` |

#### Response
List with each payment report processed

```javascript
 [
    {
        "userId": 4,
        "payment": 100.00,
        "disbursementAmount": 97.5
    },
    {
        "userId": 1,
        "payment": 100.00,
        "disbursementAmount": 97.5
    },
    {
        "userId": 4,
        "payment": 300.00,
        "disbursementAmount": 292.5
    }
]
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 201 | `CREATED` |

## 8: **User report**

```http
POST /api/clip/usersWithPayments/{userId}/report
```
#### Parameter
user Id

```http
4
```
#### Response
Report with new payments count, all payments count and sum new payments amount
```javascript
 {
    "user": {
        "id": 4,
        "name": "Videl"
    },
    "payments_sum": 3,
    "new_payments": 1,
    "new_payments_amount": 860.0
}
```
#### Status Code

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 404 | `USER NOT FOUND` |


