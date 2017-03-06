CREATE DATABASE PhoneBook;
USE PhoneBook;



CREATE TABLE item (
 ItemId   INT NOT NULL AUTO_INCREMENT PRIMARY KEY
,fullname varchar(30)
,name     VARCHAR(30)
,surname  varchar(30)
);


create table phone(
	PhoneId   INT NOT NULL AUTO_INCREMENT PRIMARY KEY
   ,itemid    int
   ,phone     varchar(30)
);

