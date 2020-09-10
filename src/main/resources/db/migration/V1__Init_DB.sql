drop table if exists convert cascade;
drop table if exists currency cascade;
drop table if exists users cascade;
drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start 1 increment 1;

create table convert (
    id int8 not null default nextval('hibernate_sequence'),
    amount float8 not null check (amount>=0),
    date date not null,
    result float8 not null,
    amount_curr_id int8 not null,
    result_curr_id int8 not null,
    user_id int8 not null,
    primary key (id)
);

create table currency (
    id int8 not null default nextval('hibernate_sequence'),
    chr_code varchar(3),
    curr_name varchar(255),
    nominal int4 check (nominal>=0),
    num_code varchar(3),
    primary key (id)
);

create table rate (
    id int8 not null default nextval('hibernate_sequence'),
    date date not null,
    value float8 not null check (value>=0),
    currency_id int8 not null,
    primary key (id)
);

create table users (
    id int8 not null default nextval('hibernate_sequence'),
    password varchar(255),
    username varchar(255),
    primary key (id)
);

alter table convert add constraint FKoouah02l25k3wj3w24jopmr0c foreign key (amount_curr_id) references currency;
alter table convert add constraint FKrg7trglutb3s7n6m3hp1badg2 foreign key (result_curr_id) references currency;
alter table convert add constraint FKk6t8qkg3b7arqs58gb9j5lacg foreign key (user_id) references users;
alter table rate add constraint FKtxu55ef1y7xv2m47t8nm85d9 foreign key (currency_id) references currency;