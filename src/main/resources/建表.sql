create table dish
(
    id          bigint auto_increment
        primary key,
    name        varchar(64)          null,
    price       decimal              null,
    picture     varchar(128)         null,
    description varchar(128)         null,
    enable      tinyint(1) default 0 null,
    constraint dish_name_uindex
        unique (name)
);

create table dish_rate
(
    id      bigint auto_increment
        primary key,
    dish_id bigint   null,
    demand  int      null,
    time    datetime null
);

create table user
(
    id         bigint auto_increment
        primary key,
    username   varchar(64)             not null,
    password   varchar(64)             not null,
    name       varchar(64)             null,
    age        int        default 0    null,
    gender     varchar(4) default '男' null,
    is_manager tinyint(1) default 0    not null,
    avatar     varchar(128)            null,
    constraint username
        unique (username)
);

create table user_rate
(
    id      bigint auto_increment
        primary key,
    user_id bigint   null,
    dish_id bigint   null,
    time    datetime null
);

