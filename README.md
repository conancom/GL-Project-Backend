# GL Project

Created with Java Spring Boot 🍃

Deployed on [Vercel](https://gl-project.vercel.app) 🚀

GL Project is a gaming library collection application where you can view all of your games in one place (Only steam is supported). Just a fun little side project to get into web development. This repo is for the BE of GL Project, check out the [frontend](https://github.com/charnar/GL-Project-Frontend) as well!

## Setup

#### Prerequisites

- JDK 18

- Postgres

1. Create a Database in Postgres with the name `ProjectGL` either with:

```
CREATE DATABASE ProjectGL
```
or your UI tool of choice (We used DBeaver)

2. Copy and rename the `application.properties-t` file to `application.properties`
3. Set your datasource variables corresponding to your setup:

```
spring.datasource.username=YOUR_POSTGRE_USERNAME
spring.datasource.password=YOUR_POSTGRES_PASSWORD
```

3. Run `BackendApplication.java` and enjoy on http://localhost:8080

## Resources

- [GL Project Frontend - @charnar](https://github.com/charnar/GL-Project-Frontend)
- [Baeldung](https://www.baeldung.com/spring-boot)
