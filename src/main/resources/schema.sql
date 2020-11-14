create table unit(
	code varchar(255) primary key,
	description varchar(255) not null 
);

create table item(
	sku varchar(255) primary key,
	name varchar(255) not null,
	unit_price real not null,
	unit_code varchar(255) not null
);

alter table item add constraint unit_fk foreign key (unit_code) references unit (code) on update cascade on delete restrict;