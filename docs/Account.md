# Account API Spec

## Get All Account

Endpoint : GET /api/accounts/{userId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : [
    {
      "id": "someid",
      "name": "Bank XYZ",
      "type": "Debit"
    },
    {
      "id": "someid",
      "name": "Bank ABC",
      "type": "Credit"
    },
    {
      "id": "someid",
      "name": "Wallet",
      "type": "Cash"
    },
    ...
  ]
}
```

Response Body (Failed) :

```json
{
  "errors" : "No Account yet"
}
```

## Add Account

Endpoint : POST /api/accounts/{userId}/create

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

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

## Update Account

Endpoint : PUT /api/accounts/{userId}/update/{accountId}

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "name" : "Updated Name",
  "type" : "Debit"
}
```

Response Body (Success) :

```json
{
  "data" : {
      "id": "someid",
      "name": "Updated Name",
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

## Delete Account

Endpoint : DELETE /api/accounts/{userId}/delete/{accountId}

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