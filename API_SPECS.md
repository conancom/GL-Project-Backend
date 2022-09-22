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
  "libraries" : [
    {
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": "8ysjasdhasbd8o7sy87tg324",
      "games": [
        {
          "game_name": "Overwatch",
          "game_id": "asd8y7834yhnhds",
          "game_description": "This is a good game made by good ppl",
          "picture_url": "https//pic.com",
          "banner_url": "https//pic.com"
        },
        {
          "game_name": "Minecraft",
          "game_id": "938isjdfjghshdasd",
          "game_description": "Minecraft, what else is there to say!",
          "picture_url": "https//pic.com",
          "banner_url": "https//pic.com"
        }
      ]
    },
    {
      "library_name": "GOG", //UBISOFT, EPIC, ETC
      "library_id": "askdjhna8907dajbnsaasd",
      "games": [
        {
          "game_name": "Overwatch",
          "game_id": "0984oksjmndfj8asd",
          "game_description": "This is a good game made by good ppl",
          "picture_url": "https//pic.com",
          "banner_url": "https//pic.com"
        },
        {
          "game_name": "Minecraft",
          "game_id": "kajd89a7sdkjabsdiuas",
          "game_description": "Minecraft, what else is there to say!",
          "picture_url": "https//pic.com",
          "banner_url": "https//pic.com"
        }
      ]
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
  "game_id": "asd80973jklhf897yhnbjkbufygdfa",
  "session_id": "layusd5652mnzxbc3viggweyutabh8974"
}
```