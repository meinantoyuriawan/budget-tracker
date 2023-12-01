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
      "id": "1",
      "name": "Bank XYZ",
      "type": "Debit"
    },
    {
      "id": "2",
      "name": "Bank ABC",
      "type": "Credit"
    },
    {
      "id": "3",
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
  "errors" : "Accounts not found"
}
```

## Add Account

Endpoint : POST /api/accounts/{userId}/create

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "account_name" : "New account",
  "account_type" : "Debit"
}
```

Response Body (Success) :

```json
{
  "data" : {
      "id": "4",
      "name": "New account",
      "type": "Debit"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User not found"
}
```

## Update Account

Endpoint : POST /api/account/{accountId}/update

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Request Body :

```json
{
  "account_name" : "Updated Name",
  "account_type" : "Debit"
}
```

Response Body (Success) :

```json
{
  "data" : {
      "id": "4",
      "name": "Updated Name",
      "type": "Debit"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User not found"
}
```

## Delete Account

Endpoint : POST /api/account/{accountId}/delete

Request Header :

- X-API-TOKEN :Token (Mandatory) # later feature, use a template for development

Response Body (Success) :

```json
{
  "data" : {
      "id": "4",
      "name": "Updated Name",
      "type": "Debit"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User not found"
}
```