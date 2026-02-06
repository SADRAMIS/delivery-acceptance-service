# Delivery Acceptance Service

Сервис приёмки поставок фруктов от поставщиков и формирования отчётов по весу и стоимости.

## Стек

- Java 17
- Spring Boot 4.0.2
- Spring Data JPA (Hibernate)
- PostgreSQL
- Maven

## Запуск

1. Создать БД `delivery_db` в PostgreSQL.
2. Настроить подключение в `src/main/resources/application.properties`.
3. Запустить приложение:
   ```bash
   mvn spring-boot:run

## Пример запроса (Postman / curl)

### 1. Приёмка поставки от Поставщика 1

`POST /api/deliveries`

Body (JSON):

```json
{
  "supplierId": 1,
  "deliveryDate": "2026-02-06T10:00:00",
  "items": [
    { "productId": 1, "weightKg": 10.5 },
    { "productId": 2, "weightKg": 20.0 }
  ]
}

curl -X POST "http://localhost:8080/api/deliveries" \
  -H "Content-Type: application/json" \
  -d '{
    "supplierId": 1,
    "deliveryDate": "2026-02-06T10:00:00",
    "items": [
      { "productId": 1, "weightKg": 10.5 },
      { "productId": 2, "weightKg": 20.0 }
    ]
  }'

