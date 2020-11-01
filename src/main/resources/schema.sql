create table unit(
	id varchar(255) primary key,
	description varchar(255) not null 
);

create table item(
	sku varchar(255) primary key,
	name varchar(255) not null,
	unit_price real not null,
	unit_id varchar(255) not null
);

alter table item add constraint unit_fk foreign key (unit_id) references unit (id) on update cascade on delete restrict;