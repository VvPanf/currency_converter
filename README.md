# Тестовое задание на вакансию Java разработчика
# Конвертер валют

**Используемые технологии (строгое соблюдение):**

- Java 11 (или последняя)
- Maven
- Spring boot 2 (или последняя версия)
- PostgresSQL 12 (или последняя версия)
- Html 5\CSS (Можно Thymeleaf, React.js, Angular.js, Vue.js и.т.д)

**Задача:**

Сделать конвертер валют

**Описание:**

При запуске приложения, необходимо получить список актуальных валют и их курсов с сайта ЦБРФ http://www.cbr.ru/scripts/XML_daily.asp (дополнительная информация https://cbr.ru/development/sxml/) и записать их в базу данных (индентификаторы, коды, названия), а так же курсы (*привязанные к валюте*) на дату запроса. В конвертере должна быть авторизация по логину и паролю. Пользователь пройдя авторизацию попадает на главный экран, где может выбрать из какой валюты и в какую будет конвертация, указывает количество переводимых средств и нажимает кнопку "Конвертировать". После чего происходит запрос в БД на получение актуального курса на ***текущую дату ***, если данные на текущую дату отсутствуют, необходимо, произвести получение курсов с сайта ЦБ и добавить новые записи в базу данных. Также в конвертере должна вестись история произведенных конвертаций с записью в базу данных со ссылкой на курс по которой была произведена конвертаци. Историю можно посмотреть на той же странице конвертера или отдельной вкладке (возможна реализация базовых фильтров). Остальная функциональность и визуал по желанию.

**Рекомендации для сборки и запуска проекта:
- Создать на сервере базы данных пользователя **convert_user** и паролем **1234**
```sql
CREATE ROLE convert_user WITH
  LOGIN
  NOSUPERUSER
  CREATEDB
  NOCREATEROLE
  INHERIT
  REPLICATION
  CONNECTION LIMIT -1
  PASSWORD '1234';
```
- Создать на сервере базу данны **currency-converter**
```sql
  CREATE DATABASE "currency-converter"
    WITH 
    OWNER = convert_user
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```
- Создать таблицы и заполнить в них необходимые данные
```sql
  create table if not exists Users (
	user_id serial primary key,
	user_name varchar(20) not null,
	user_passwd varchar(20) not null
);

create table if not exists Currency_list (
	curr_id serial primary key,
	num_code varchar(3) not null,
	code varchar(3) not null,
	curr_name varchar(255) not null,
	nominal integer not null
);
	
create table if not exists Rate (
	rate_id serial primary key,
	curr_id integer not null,
	curr_value real not null,
	rate_date date not null
);

create table if not exists Convert_history (
	ch_id serial primary key,
	user_id integer,
	from_curr integer,
	to_curr integer,
	from_value real,
	to_value real,
	c_date date
);

insert into Users(user_name, user_passwd)
values ('admin', 'admin');

insert into currency_list
values (1,643,'RUB','Российский рубль',1.);
```
- Создать исполняемый **jar** файл командой **mvn clean package**
- Для запуска **jar** файла из командной строки необходимо перейти в папку с **jar** файлом и воспользоваться командой **java -jar test-1.0-SNAPSHOT.jar**
