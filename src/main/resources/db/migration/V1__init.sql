create table app_users
(
    id       int4 generated always as identity,
    username varchar(100) not null,
    email    varchar(100) not null,
    password varchar(100) not null,
    constraint app_users_pk primary key (id)
);

create table groups
(
    id          int4 generated always as identity,
    name        varchar(100) not null,
    description varchar(255),
    created_at  timestamptz,
    user_id     int4 not null,
    constraint groups_pk primary key (id),
    constraint app_users_fk foreign key (user_id) references app_users (id)
);

create table decks
(
    id          int4 generated always as identity,
    created_at  timestamptz,
    name        varchar(100) not null,
    description varchar(255),
    group_id    integer not null,
    constraint decks_pk primary key (id),
    constraint groups_fk foreign key (group_id) references groups (id)
);

create table flash_card_statistics
(
    id                        int4 generated always as identity,
    correct_answers           int4 not null default 0,
    days_to_next_learn        int4,
    next_learn_date           date,
    primary_memorization_date date,
    constraint flash_cards_statistics_pk primary key (id)
);

create table flash_cards
(
    id                       int4 generated always as identity,
    back_side                varchar(255) not null,
    front_side               varchar(255) not null,
    examples                 text,
    created_at               timestamptz,
    updated_at               timestamptz,
    deck_id                  int4 not null,
    flash_card_statistics_id int4,
    constraint flash_cards_pk primary key (id),
    constraint decks_fk foreign key (deck_id) references decks (id),
    constraint flash_card_statistics_fk foreign key (flash_card_statistics_id)
        references flash_card_statistics (id)
);