# Expenses API Spec

## Get All Expenses

Endpoint : GET /api/expenses/{userId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "someid",
      "date": "expensesDate",
      "account": "accountId1",
      "amount": 20000,
      "type": "Food",
      "description": "Expenses description"
    },
    {
      "id": "someid",
      "date": "expensesDate",
      "account": "accountId2",
      "amount": 200000,
      "type": "Housing",
      "description": "Expenses description"
    },
    {
      "id": "someid",
      "date": "expensesDate",
      "account": "accountId3",
      "amount": 50000,
      "type": "Medical & Healthcare",
      "description": "Expenses description"
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Expenses yet"
}
```

### Grouped by date (#later feature)

Endpoint : GET /api/expenses/{userId}/date/{timeVariable}/

timeVariable : year, month, week, day

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "someid",
      "date": "expensesDatebyMonth",
      "account": "accountId1",
      "amount": 20000,
      "type": "Food",
      "description": "Expenses description"
    },
    {
      "id": "someid",
      "date": "expensesDatebyMonth",
      "account": "accountId2",
      "amount": 200000,
      "type": "Housing",
      "description": "Expenses description"
    },
    {
      "id": "someid",
      "date": "expensesDatebyMonth",
      "account": "accountId3",
      "amount": 50000,
      "type": "Medical & Healthcare",
      "description": "Expenses description"
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Expenses yet"
}
```

### Filtered by accountId

Endpoint : GET /api/expenses/{userId}/account/{accountId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "someid",
      "date": "expensesDate",
      "account": "accountId",
      "amount": 20000,
      "type": "Food",
      "description": "Expenses description"
    },
    {
      "id": "someid",
      "date": "expensesDate",
      "account": "accountId",
      "amount": 200000,
      "type": "Housing",
      "description": "Expenses description"
    },
    {
      "id": "someid",
      "date": "expensesDate",
      "account": "accountId",
      "amount": 50000,
      "type": "Medical & Healthcare",
      "description": "Expenses description"
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Expenses yet"
}
```

## Add Expenses

Endpoint : POST /api/expenses/{userId}/create

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

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

## Update Expenses

Endpoint : PUT /api/expenses/{userId}/update/{expensesId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "date": "dd/mm/yyyy",
  "account": "accountId",
  "amount": 33000,
  "type": "newType",
  "description": "New description"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "expensesId",
    "date": "newExpensesDate",
    "account": "newAccountId",
    "amount": 33000,
    "type": "newType",
    "description": "New description"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Expenses doesn't exist"
}
```

## Delete Account

Endpoint : DELETE /api/expenses/{userId}/delete/{expensesId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data": "Ok"
}
```

Response Body (Failed) :

```json
{
  "errors" : "Expenses doesn't exist"
}
```