# Goal API Spec

## Get All Goal

Endpoint : GET /api/goals/{userId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "someid",
      "time": "Weekly",
      "acc": "acc1",
      "amount": 350000
    },
    {
      "id": "someid",
      "time": "Monthly",
      "acc": "acc2",
      "amount": 2000000
    },
    {
      "id": "someid",
      "time": "Yearly",
      "acc": "acc3",
      "amount": 30000000
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Goal yet"
}
```

### Filtered by accountId

Endpoint : GET /api/goals/{userId}/account/{accountId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "someid",
      "time": "Weekly",
      "acc": "acc1",
      "amount": 350000
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Goal yet"
}
```

## Get Goal

Endpoint : GET /api/goal/{goalId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "time": "Weekly",
    "acc": "acc1",
    "amount": 350000
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Goal not found"
}
```

## Get User Goal Information

Endpoint : GET /api/goal/{goalId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : {
    "daily": "this user has x amount left towards daily goal",
    "weekly": "this user has x amount left towards weekly goal",
    "monthly": "this user has x amount left towards monthly goal",
    "yearly": "this user has x amount left towards yearly goal"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Goal not found"
}
```

## Add Goal

Endpoint : POST /api/goal/{userId}/create

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "time" : "Weekly",
  "acc" : "acc2",
  "amount" : 20000
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "time": "Weekly",
    "acc": "acc2",
    "amount": 20000
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User doesn't exist"
}
```

## Update Goal

Endpoint : PUT /api/goal/{userId}/update/{goalId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "time" : "new Time",
  "acc" : "newAcc",
  "amount" : "newAmount"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "time": "new Time",
    "acc": "newAcc",
    "amount": "newAmount"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User doesn't exist"
}
```

## Delete Goal

Endpoint : DELETE /api/goal/{userId}/delete/{goalId}

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
  "errors" : "User doesn't exist"
}
```