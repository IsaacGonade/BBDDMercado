DROP DATABASE IF EXISTS Mercado;
CREATE DATABASE Mercado;
Use Mercado;


CREATE TABLE Productos(
Id int unsigned auto_increment primary key,
Precio int(3),
Marca varchar(20),
Nombre varchar(20),
Tipo enum('Carne','Pescado','Verdura','Fruta','Lacteos')
);


 INSERT INTO Productos VALUES
 (1,10,"Danone","Yogurt","Lacteos"),
 (2,15,"ElPozo","CarneDeVacuno","Carne"),
 (3,5,"Royal","SalmonAhumado","Pescado"),
 (4,6,"ElPuerto","Dorada","Pescado"),
 (5,9,"Campofrio","PavoBraseado","Carne"),
 (6,8,"Trevijano","SopaVerduras","Verdura"),
 (7,4,"Findus","Espinacas","Verdura"),
 (8,12,"PlatanoDeCanarias","Platano","Fruta"),
 (9,11,"Kanzi","Manzana","Fruta"),
 (10,7,"Pascual","LecheEntera","Lacteos");



 CREATE TABLE Usuarios(
 usuario varchar(20) primary key,
 pass varchar(20)
 );

INSERT INTO Usuarios VALUES
 ("Isaac", "Gonade");