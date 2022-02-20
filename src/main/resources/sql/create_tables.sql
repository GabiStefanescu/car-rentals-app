drop schema if exists car_rentals_javafx;

create schema car_rentals_javafx;

use car_rentals_javafx;

create table if not exists vehicles
(
    id            int auto_increment primary key,
    serial_number int(10)     not null,
    model         varchar(30) not null,
    rented_status BOOLEAN not null ,
    price double not null,
    rented_date date,
    returned_date date,
    customer_id int references customers (id)
);

create table if not exists customers
(
  id int auto_increment primary key,
  first_name varchar(40) not null,
  last_name  varchar(40) not null,
  phone_number varchar(14) not null,
  vehicle_id int references vehicles (id)
);

create table if not exists admins
(
  id int auto_increment primary key,
  username varchar(255),
  password varchar(255)
);
