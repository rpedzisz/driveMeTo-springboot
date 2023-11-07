create table car (
car_id bigint not null auto_increment,
brand varchar(255),
car_status varchar(255),
current_localization varchar(255),
model varchar(255),
driver_driver_id bigint,
primary key (car_id));

create table company (
company_id bigint not null auto_increment,
primary key (company_id));

create table course (
course_id bigint not null auto_increment,
chosen_route varchar(255),
course_grade bigint,
course_status varchar(255),
destination_localization varchar(255),
distance double precision,
fee double precision,
start_localization varchar(255),
car_car_id bigint,
driver_driver_id bigint,
user_user_id bigint,
primary key (course_id));

create table driver (
driver_id bigint not null auto_increment,
avg_grade double precision,
name varchar(255),
price_per_km double precision,
car_car_id bigint,
primary key (driver_id));

create table driver_driver_languages_set (
driver_driver_id bigint not null,
driver_languages_set varchar(255));

create table user (
user_id bigint not null auto_increment,
current_location varchar(255),
username varchar(255),
primary key (user_id));

alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table car add constraint FKboj59oecgra1me6t0p9bfq5y2 foreign key (driver_driver_id) references driver (driver_id);
alter table course add constraint FKs1yx02agj35hsut6y1dmodd16 foreign key (car_car_id) references car (car_id);
alter table course add constraint FKci9am4bndfr0rndyx50g4xctb foreign key (driver_driver_id) references driver (driver_id);
alter table course add constraint FKtam9yqcyratrryxc1nqjkma3r foreign key (user_user_id) references user (user_id);
alter table driver add constraint FKdqy81v3f93grojcq2js0vj7pw foreign key (car_car_id) references car (car_id);
alter table driver_driver_languages_set add constraint FK7vypa50fu35n4gck0nl3bqq20 foreign key (driver_driver_id) references driver (driver_id);
