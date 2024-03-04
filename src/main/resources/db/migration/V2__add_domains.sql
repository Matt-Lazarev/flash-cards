create table domains
(
    id          int4 generated always as identity,
    name        varchar(100) not null,
    description varchar(255),
    created_at  timestamptz,
    user_id     int4 not null,
    constraint domains_pk primary key (id)
);

alter table groups
drop constraint if exists app_users_fk;

alter table groups
drop column if exists user_id;

alter table domains
add constraint app_users_fk foreign key (user_id) references app_users (id);

alter table groups
add column domain_id int;

alter table groups
add constraint domain_fk foreign key (domain_id) references domains (id);



