Web-сервис CustomerService

**Установка:**
1. Создать пользователя Postgresql:

`CREATE USER customer WITH PASSWORD '123';`
2. Создать бд customer_service, customer_service_test:

`CREATE DATABASE customer_service;`

`ALTER DATABASE customer_service OWNER TO customer;`

`CREATE DATABASE customer_service_test;`

`ALTER DATABASE customer_service_test OWNER TO customer;`
3. Создать таблицы customer и address в каждой бд:

```
CREATE TABLE address (
id bigserial NOT NULL,
country varchar(255),
region varchar(255),
city varchar(255),
street varchar(255),
house varchar(255),
flat varchar(255),
created timestamp,
modified timestamp,
CONSTRAINT pk_address PRIMARY KEY (id)
);
```
```
ALTER TABLE address OWNER TO customer;
CREATE TABLE customer (
id bigserial NOT NULL,
registred_address_id bigint NOT NULL,
actual_address_id bigint NOT NULL,
first_name varchar(255) NULL,
last_name varchar(255) NULL,
middle_name varchar(255) NULL,
sex varchar(6) NOT NULL,
CONSTRAINT ck_customer_sex CHECK (((sex)::text = ANY ((ARRAY['male'::character
varying, 'female'::character varying])::text[]))),
CONSTRAINT pk_customer PRIMARY KEY (id),
CONSTRAINT fk_registred_address_id FOREIGN KEY (registred_address_id)
REFERENCES address(id) ON UPDATE RESTRICT ON DELETE RESTRICT,
CONSTRAINT fk_actual_address_id FOREIGN KEY (actual_address_id) REFERENCES
address(id) ON UPDATE RESTRICT ON DELETE RESTRICT
);
ALTER TABLE customer OWNER TO customer;
```
**Запуск**
В корневой папки открыть терминал и выполнить команды:

`mvn clean install`

`java -jar target/ms/customer.jar`

**Команды**

Зайти в Google Chrome, открыть Console(shift+f12):

```
fetch('/customers', {method: 'POST', headers: {'Content-Type': 'application/json'}, 
body: JSON.stringify({"firstName":"Po", "lastName":"Po", "middleName":"Po", "sex":"female",
 "registeredAddress": {"country": "london"}, "actualAddress": {}})}).then(result => console.log(result))
 ```
```
fetch('/customers/1', {method: 'PUT', headers: {'Content-Type': 'application/json'},
  body: JSON.stringify({"country": "Геленджик"})}).then(result => console.log(result))
```