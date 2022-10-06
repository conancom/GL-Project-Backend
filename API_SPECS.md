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
#### [POST] /api/v1/all-library-games
```json
{
  "session_id": "layusd5652mnzxbc3viggweyutabh8974"
}
```
##### Response
```json
{
  "status": "SESSION_KEY_OK", //SESSION_EXPIRED, USER_DOES_NOT_EXIST
  "games": [
    {
      "game_name": "Overwatch",
      "game_id": "asd8y7834yhnhds",
      "personal_game_id": "asdihasidhasd",
      "game_description": "This is a good game made by good ppl",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": "8ysjasdhasbd8o7sy87tg324"
    },
    {
      "game_name": "Minecraft",
      "game_id": "938isjdfjghshdasd",
      "personal_game_id": "asdihasidhasd",
      "game_description": "Minecraft, what else is there to say!",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": "8ysjasdhasbd8o7sy87tg324"
    },
    {
      "game_name": "Overwatch",
      "game_id": "0984oksjmndfj8asd",
      "personal_game_id": "asdihasidhasd",
      "game_description": "This is a good game made by good ppl",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "GOG", //UBISOFT, EPIC, ETC
      "library_id": "askdjhna8907dajbnsaasd"
    },
    {
      "game_name": "Minecraft",
      "game_id": "kajd89a7sdkjabsdiuas",
      "personal_game_id": "asdihasidhasd",
      "game_description": "Minecraft, what else is there to say!",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "GOG", //UBISOFT, EPIC, ETC
      "library_id": "askdjhna8907dajbnsaasd"
    }
  ]
}
```
#### [POST] /api/v1/library-games
```json
{
  "library_id": "8ysjasdhasbd8o7sy87tg324",
  "session_id": "layusd5652mnzxbc3viggweyutabh8974"
}
```
#### [POST] /api/v1/game-info
```json
{
  "status": "SESSION_KEY_OK", //SESSION_EXPIRED, USER_DOES_NOT_EXIST
  "game_id": "asd80973jklhf897yhnbjkbufygdfa",
  "session_id": "layusd5652mnzxbc3viggweyutabh8974"
}
```