drop table if exists planets;
create table planets (
    id int auto_increment primary key,
    name varchar(10) not null
);
insert into planets (name) values ('Earth');
insert into planets (name) values ('Mars');
insert into planets (name) values ('Jupiter');
insert into planets (name) values ('Venus');