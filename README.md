# Budget Tracker API

**Personal Budget Tracker API**

This is my attempts to learn SpringBoot by creating a simple project

## Purpose
This api provides a backend for a personal budget tracker. It will allow you do CRUD operations on:
- User
- Accounts
- Expenses

## Technology
- Java SpringBoot
- MySQL database to store the data

## API documentations
### Account API
#### Add Account

Endpoint : POST /api/accounts/{userId}/create

Request Body :

```json
{
  "name" : "New account",
  "type" : "Debit"
}
```

Response Body (Success) :

```json
{
  "data" : {
      "id": "someid",
      "name": "New account",
      "type": "Debit"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User doesn't exist"
}
```

See more [Account API Spec](/docs/Account.md)
### Expenses API
#### Add Expenses

Endpoint : POST /api/expenses/{userId}/create

Request Body :

```json
{
  "date": "dd/mm/yyyy",
  "account": "accountId",
  "amount": 20000,
  "type": "Urgent",
  "description": "Expenses description"
}
```

Response Body (Success) :

```json
{
  "data" : {
        "id": "expensesId",
        "date": "expensesDate",
        "account": "accountId",
        "amount": 20000,
        "type": "Urgent",
        "description": "Expenses description"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User doesn't exist"
}
```

See more [Expenses API Spec](/docs/Expenses.md)

## On going Features
- Scheduled Expenses
- Time-bound Expenses Goal

## Future Features
- JWT Authentication
- Get Expenses grouped by date