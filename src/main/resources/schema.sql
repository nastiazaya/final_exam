CREATE DATABASE IF NOT EXISTS game_platform;
USE game_platform;

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL,
    name VARCHAR (100) NOT NULL ,
    lastName VARCHAR (100) NOT NULL,
    countryOfOrigin VARCHAR(50) NOT NULL,
    email VARCHAR(20) NOT NULL
    );