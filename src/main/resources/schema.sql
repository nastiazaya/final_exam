/*CREATE DATABASE IF NOT EXISTS game_platform;
USE game_platform;*/

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL,
    name VARCHAR (100) NOT NULL ,
    last_name VARCHAR (100) NOT NULL,
    country_of_origin VARCHAR(50) NOT NULL,
    email VARCHAR(20) NOT NULL
);