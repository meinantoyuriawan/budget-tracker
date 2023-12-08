# Schedule API Spec

## Get All Schedule

Endpoint : GET /api/schedules/{userId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "scheduleId",
      "user": "user id",
      "expenses": "expenses id",
      "start": "start date",
      "end": "end date",
      "byTime": "Weekly"
    },
    {
      "id": "scheduleId",
      "user": "user id",
      "expenses": "expenses id",
      "start": "start date",
      "end": "end date",
      "byTime": "Weekly"
    },
    {
      "id": "scheduleId",
      "user": "user id",
      "expenses": "expenses id",
      "start": "start date",
      "end": "end date",
      "byTime": "Weekly"
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Schedule yet"
}
```

## Get Schedule

Endpoint : GET /api/schedule/{scheduleId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : {
    "id": "scheduleId",
    "user": "user id",
    "expenses": "expenses id",
    "start": "start date",
    "end": "end date",
    "byTime": "Weekly"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Schedule not found"
}
```

## Add Schedule

Endpoint : POST /api/schedule/{expensesId}/create

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "start" : "start date",
  "end" : "end date",
  "byTime": "Weekly"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "user": "user id",
    "expenses": "expenses id",
    "start": "start date",
    "end": "end date",
    "byTime": "Weekly"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Expenses not found"
}
```

## Update Account

Endpoint : PUT /api/schedule/{expensesId}/update

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "start" : "new start date",
  "end" : "new end date",
  "byTime": "new time"
}
```

Response Body (Success) :

```json
{
  "data" : {
    "id": "someid",
    "user": "user id",
    "expenses": "expenses id",
    "start": "new start date",
    "end": "new end date",
    "byTime": "new time"
  }
}
```

Response Body (Failed) :

```json
{
  "errors" : "Schedule not found"
}
```

## Delete Account

Endpoint : DELETE /api/schedule/{expensesId}/delete/

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