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

In order to create a new account with given balances, you can run the following example curl:
```shell
curl --location 'localhost:8000/accounts' \
--header 'Content-Type: application/json' \
--data '{
	"foodBalance": 100.0,
    "mealBalance": 200.0,
    "cashBalance": 0.0
}'
```

In order to try to authorize a transaction, you can run the following example curl:
```shell
curl --location 'localhost:8000/transactions' \
--header 'Content-Type: application/json' \
--data '{
	"account": "1",
	"totalAmount": 50.00,
	"mcc": "5812",
	"merchant": "PAG*JoseDaSilva          RIO DE JANEI BR"
}'
```
