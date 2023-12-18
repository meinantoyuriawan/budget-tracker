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
      "by_time": "Weekly",
      "by_acc": "acc1",
      "amount": 350000
    },
    {
      "id": "someid",
      "by_time": "Monthly",
      "by_acc": "acc2",
      "amount": 2000000
    },
    {
      "id": "someid",
      "by_time": "Yearly",
      "by_acc": "acc3",
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
      "by_time": "Weekly",
      "by_acc": "acc1",
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
    "by_time": "Weekly",
    "by_acc": "acc1",
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

## Add Goal

Endpoint : POST /api/goal/{userId}/create

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "by_time" : "Weekly",
  "by_acc" : "acc2",
  "amount" : 20000
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "by_time": "Weekly",
    "by_acc": "acc2",
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
  "by_time" : "new Time",
  "by_acc" : "newAcc",
  "amount" : "newAmount"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "by_time": "new Time",
    "by_acc": "newAcc",
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