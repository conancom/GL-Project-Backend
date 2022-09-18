# API Specifications

#### [POST] /api/v1/register
##### Request
```json
{
  "username": "username1",
  "email": "user@email.com",
  "password": "password1"
}
```
##### Response
```json
{
  "status": "SUCCESS", //DUPLICATE_EMAIL, DUPLICATE_USERNAME
  "username": "username1"
}
```

#### [POST] /api/v1/login
##### Request
```json
{
  "username_email": "username1", //user@email.com
  "password": "password1"
}
```
##### Response
```json
{
  "status": "SUCCESS", //INVALID_EMAIL, INVALID_USERNAME,INVALID_PASSWORD
  "username": "username1",
  "session_id": "layusd5652mnzxbc3viggweyutabh8974"
}
```
#### [GET] /api/v1/user
