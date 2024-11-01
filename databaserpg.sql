CREATE DATABASE databasergp;
CREATE USER 'Tin4o'@'localhost' IDENTIFIED BY 'Shtepegnaviktor1!';
GRANT ALL PRIVILEGES ON databaserpg.* TO 'Tin4o'@'localhost';
FLUSH PRIVILEGES;

CREATE TABLE Account(
username varchar(255),
email varchar(255),
passwords varchar(255)
)



