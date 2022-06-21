Backend Spring REST проект книжного интернет магазина.

Стек: Java 11, Spring (Boot, Security, MVC), REST, PostgreSQL, Swagger UI, Active MQ.

Реализован функционал CRUD с сущностями книга, автор, клиент, пользователь, заказ. Все данные хранятся в базе данных PostgreSQL на удаленном сервере Heroku

Локальный сервер http://localhost:8080

Для удобства подключен SwaggerUI, после запуска сервера нужно перейти на endpoint http://localhost:8080/swagger-ui/index.html

Endpoints:

User - /users, /users/{login}

Order - /orders, /orders/{orderId}

Customer - /customers, /customers/{id}

Book - /api/books, /api/books/{id}

Authors - /api/authors, /api/authors/{authorId}

Для генерации отчета покупок пользователя за месяц с помощью брокера сообщений, необходимо установить
ActiveMQ на локальный сервер с сайта разработчика https://activemq.apache.org/ , снять комментирование с настроек в файле application.properties в папке resources. 
Endpoint localhost:8080/report/login, где login - логин зарегистрированного пользователя, по которому необходим отчет 

По умолчанию в базу пользователей занесены admin (пароль admin) и user (пароль user)
