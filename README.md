# Account

Account is a service which manages transactions across accounts.

## Maintainer
- Thaís Nunes
  - **Email**: <tlnunes.ti@gmail.com>
  - **Github**: <https://github.com/thaiisloopes>
  - **Linkedin**: <https://www.linkedin.com/in/thaislopesnunes/>

### Environment
| Env           | Url                   |
|---------------|:----------------------|
| Localhost     | http://localhost:8000 |

### Requirements
* [Docker](https://docs.docker.com/get-started/get-docker/)
* [Docker Compose](https://docs.docker.com/compose/install/)
* [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)

### Running locally
To run locally, you need to run the following command to build and start the database container

```shell
  docker-compose up db -d
```

After that, you can run the migrations with the following command:
```shell
  SPRING_PROFILES_ACTIVE=dbmigration ./gradlew bootRun
```

Finally, you can run the application:
```shell
  SPRING_PROFILES_ACTIVE=local ./gradlew bootRun
```
