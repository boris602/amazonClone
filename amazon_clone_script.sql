DROP DATABASE IF EXISTS amazonClone;

create database amazonClone;
USE amazonClone;


DROP TABLE  IF EXISTS users;
DROP TABLE  IF EXISTS products;
DROP TABLE  IF EXISTS purchase_history;


-- Create users table
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
	userName VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Create products table
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sold_by_id BIGINT NOT NULL,
    amount INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
	description TEXT NOT NULL,
    link VARCHAR(255),
    FOREIGN KEY (sold_by_id) REFERENCES users(id)
);


-- Create purchase_history table
CREATE TABLE purchase_history (
    history_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    prod_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    price DOUBLE NOT NULL,
    FOREIGN KEY (prod_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

select * from users;