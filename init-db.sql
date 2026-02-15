CREATE TABLE audit_logs (
    id_audit_logs VARCHAR(50) PRIMARY KEY,
    timestamp VARCHAR(150),
    user_id VARCHAR(50),
    action_type VARCHAR(50),
    entity VARCHAR(50),
    status VARCHAR(50)
);

CREATE TABLE users (
    id_users VARCHAR(50) PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) DEFAULT 'user'
);

CREATE TABLE products (
    id_products VARCHAR(50) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    cost INTEGER NOT NULL,
    stock INTEGER NOT NULL
);

CREATE TABLE orders (
    id_orders VARCHAR(50) PRIMARY KEY,
    id_users VARCHAR(50) NOT NULL,
    amount INTEGER NOT NULL
);

CREATE TABLE orders_items (
    id_orders_items VARCHAR(50) PRIMARY KEY,
    id_orders VARCHAR(50) NOT NULL,
    id_products VARCHAR(50) NOT NULL,
    quantity INTEGER NOT NULL
);