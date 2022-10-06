# GL-Project-Backend

JDK 18

Postgres Latest

### Set Up

#### Initialize Database
1) Have Postgres installed. (or Use Docker's Postgres Image)
2) Create a new Database called ```ProjectGL```
3) Execute the DDL query in ```./DB/db_dump.sql```.
   
#### Spring Setup
1) Copy `application.properties-t` and Rename to `application.properties`
2) Set the Database Variables according to your local setup:
   ```
    spring.datasource.username=$USERNAME$
    spring.datasource.password=$PASSWORD$
    ```
