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
      "account_id": "1",
      "account_name": "Bank XYZ",
      "account_type": "Debit"
    },
    {
      "account_id": "2",
      "account_name": "Bank ABC",
      "account_type": "Credit"
    },
    {
      "account_id": "3",
      "account_name": "Wallet",
      "account_type": "Cash"
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
      "account_id": "4",
      "account_name": "New account",
      "account_type": "Debit"
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
      "account_id": "4",
      "account_name": "Updated Name",
      "account_type": "Debit"
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
      "account_id": "4",
      "account_name": "Updated Name",
      "account_type": "Debit"
    }
}
```

Response Body (Failed) :

```json
{
  "errors" : "User not found"
}
```