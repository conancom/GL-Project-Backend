# GL-Project-Backend

JDK 18

Postgres Latest

### Set Up

#### Initialize Database
1) Have Postgres installed. (or Use Docker's Postgres Image)
2) Create a new Database called ```ProjectGL```
3) Execute the DDL query in ```./DB/db_dump1.sql``` and ```./DB/db_dump2.sql``` consecutively.
4) Set the Database Username and Password in `application.properties` according your local setup:
    ```
    spring.datasource.username=$USERNAME$
    spring.datasource.password=$PASSWORD$
    ```