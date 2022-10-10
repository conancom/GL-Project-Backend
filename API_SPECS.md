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
#### [POST] /api/v1/all-library-games/alphabetically
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
      "game_name": "Minecraft",
      "game_id": 298365685234,
      "personal_game_id": "asdihasidhasd",
      "game_description": "Minecraft, what else is there to say!",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": 897236428934
    },
    {
      "game_name": "Naraka",
      "game_id": 7863468523,
      "personal_game_id": "asdihasidhasd",
      "game_description": "Minecraft, what else is there to say!",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": 897236428934
    },
    {
      "game_name": "Overwatch",
      "game_id": 786238528364,
      "personal_game_id": "asdihasidhasd",
      "game_description": "This is a good game made by good ppl",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "GOG", //UBISOFT, EPIC, ETC
      "library_id": 897236428934
    }
  ]
}
```
#### [POST] /api/v1/library-games/alphabetically
```json
{
  "session_id": "layusd5652mnzxbc3viggweyutabh8974",
  "library_id": 897236428934
}
```
##### Response
```json
{
  "status": "SESSION_KEY_OK", //SESSION_EXPIRED, USER_DOES_NOT_EXIST
  "games": [
    {
      "game_name": "Minecraft",
      "game_id": 9186452678492,
      "personal_game_id": "asdihasidhasd",
      "game_description": "Minecraft, what else is there to say!",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": 897236428934
    },
    {
      "game_name": "Naraka",
      "game_id": 12731852316,
      "personal_game_id": "asdihasidhasd",
      "game_description": "Minecraft, what else is there to say!",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": 897236428934
    },
    {
      "game_name": "Overwatch",
      "game_id": 1298298365298,
      "personal_game_id": "asdihasidhasd",
      "game_description": "This is a good game made by good ppl",
      "picture_url": "https//pic.com",
      "banner_url": "https//pic.com",
      "library_name": "STEAM", //UBISOFT, EPIC, ETC
      "library_id": 897236428934
    }
  ]
}
```
#### [POST] /api/v1/game-info
```json
{
  "session_id": "layusd5652mnzxbc3viggweyutabh8974",
  "game_id": 582941298361832
}
```
##### Response
```json
{
  "status": "SESSION_KEY_OK",
  //SESSION_EXPIRED, USER_DOES_NOT_EXIST
  "game_name": "Minecraft",
  "game_id": 582941298361832,
  "personal_game_id": "asd80973jklhf897yhnbjkbufygdfa",
  "game_description": "Minecraft, what else is there to say!",
  "picture_url": "https//pic.com",
  "banner_url": "https//pic.com",
  "library_name": "STEAM", //UBISOFT, EPIC, ETC
  "library_id": 98302723907234
}
```