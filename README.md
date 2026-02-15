для запуска / развертывания нужны Docker и Docker Compose
чтобы запустить сборку надо перейти в директорию (или в папку) где находится файл docker-compose.yml  проекта и открыть консоль и вести docker-compose up

запросы
get("/orders") для просмотра всех заказов
post("/orders") для создание заказа
delete("/orders/{id}") для удаление заказа
get("/stats/orders") для просмотра большего информации. только для админа

get("/products") для просмотра всех продуктов
get("/products/{id}") для просмотра определенного продукта
post("/products") для создание продукта. только для админа
put("/products/{id}") для изменяет продукта. только для админа
delete("/products/{id}".) для удаление продукта. только для админа

post("/auth/register") для создания пользователя/аккаунт
post("/auth/login") для входа в аккаунт

структура базы данных
таблица логов
audit_logs
id_audit_logs VARCHAR(50) PRIMARY KEY,
timestamp VARCHAR(150)
user_id VARCHAR(50)
action_type VARCHAR(50)
entity VARCHAR(50)
status VARCHAR(50)
таблица пользователей
users 
id_users VARCHAR(50) PRIMARY KEY
username VARCHAR(50) UNIQUE NOT NULL
password_hash VARCHAR(255) NOT NULL
role VARCHAR(20) DEFAULT 'user'
таблица товаров
products
id_products VARCHAR(50) PRIMARY KEY
name VARCHAR(255) NOT NULL
cost INTEGER NOT NULL
stock INTEGER NOT NULL
таблица заказов
orders 
id_orders VARCHAR(50) PRIMARY KEY
id_users VARCHAR(50) NOT NULL
amount INTEGER NOT NULL
таблица о содержимом в заказе
orders_items
id_orders_items VARCHAR(50) PRIMARY KEY
id_orders VARCHAR(50) NOT NULL
id_products VARCHAR(50) NOT NULL
quantity INTEGER NOT NULL
