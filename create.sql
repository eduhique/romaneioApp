create sequence client_sequence start 1 increment 1;
create sequence order_item_sequence start 1 increment 1;
create sequence order_sequence start 1 increment 1;
create sequence product_conversion_type_sequence start 1 increment 1;
create sequence product_primitive_type_sequence start 1 increment 1;
create sequence product_sequence start 1 increment 1;
create sequence role_sequence start 1 increment 1;
create sequence romaneio_sequence start 1 increment 1;
create sequence user_role_sequence start 1 increment 1;
create sequence user_sequence start 1 increment 1;

create table client
(
    id           int8         not null,
    client_type  varchar(255) not null,
    comments     varchar(255),
    created_date timestamp,
    district     varchar(100),
    last_update  timestamp,
    name         varchar(255) not null,
    primary key (id)
);

create table order_item
(
    id                int8   not null,
    amount            float8 not null check (amount >= 0),
    order_id          int8   not null,
    product_id        int8   not null,
    primitive_type_id int8   not null,
    primary key (id)
);

create table product
(
    id              int8         not null,
    created_date    timestamp,
    last_update     timestamp,
    name            varchar(150) not null,
    product_type_id int8         not null,
    primary key (id)
);

create table product_conversion_type
(
    id                        int8   not null,
    from_primary              float8 not null check (from_primary >= 1),
    to_target                 float8 not null check (to_target >= 1),
    product_type_id           int8   not null,
    product_primitive_type_id int8   not null,
    primary key (id)
);

create table product_primitive_type
(
    dtype      varchar(31)  not null,
    id         int8         not null,
    is_float   boolean      not null,
    long_name  varchar(150) not null,
    short_name varchar(5)   not null,
    primary key (id)
);

create table ro_order
(
    id           int8 not null,
    created_date timestamp,
    last_update  timestamp,
    order_status varchar(255),
    status_date  timestamp,
    client_id    int8 not null,
    romaneio_id  int8 not null,
    users_id     int8 not null,
    primary key (id)
);

create table ro_user
(
    id           int8         not null,
    created_date timestamp,
    function     varchar(255) not null,
    last_update  timestamp,
    name         varchar(150) not null,
    nickname     varchar(50)  not null,
    password     varchar(255) not null,
    primary key (id)
);

create table role
(
    id        int8         not null,
    role_name varchar(100) not null,
    primary key (id)
);

create table romaneio
(
    id           int8         not null,
    comments     varchar(255),
    created_date timestamp,
    last_update  timestamp,
    name         varchar(255) not null,
    order_status varchar(255),
    status_date  timestamp,
    primary key (id)
);

create table user_role
(
    id      int8 not null,
    role_id int8 not null,
    user_id int8 not null,
    primary key (id)
);

alter table ro_user
    add constraint UK_o9pacqt6utru8jmhr7as1j9rr unique (nickname);

alter table role
    add constraint UK_iubw515ff0ugtm28p8g3myt0h unique (role_name);

alter table order_item
    add constraint FKieyde759cpdc1cwvtxm5xjp60
        foreign key (order_id)
            references ro_order;

alter table order_item
    add constraint FK551losx9j75ss5d6bfsqvijna
        foreign key (product_id)
            references product;

alter table order_item
    add constraint FKjp13jxk6tbne96855d4ijwrav
        foreign key (primitive_type_id)
            references product_primitive_type;

alter table product
    add constraint FKh9msg2f5sgoc1etus6d6qcjq3
        foreign key (product_type_id)
            references product_primitive_type;

alter table product_conversion_type
    add constraint FKdbnf4s5kwxmnihexpp1dvpa9w
        foreign key (product_type_id)
            references product_primitive_type;

alter table product_conversion_type
    add constraint FKd3v17ahglvcmoyuble0gh7edy
        foreign key (product_primitive_type_id)
            references product_primitive_type;

alter table ro_order
    add constraint FKl4of9340to75dj01cep6k5lic
        foreign key (client_id)
            references client;

alter table ro_order
    add constraint FKv20wj6hy9v0qy6n3acqwagth
        foreign key (romaneio_id)
            references romaneio;

alter table ro_order
    add constraint FK30vejjh7yntp5eg5nheq82u8g
        foreign key (users_id)
            references ro_user;

alter table user_role
    add constraint FKa68196081fvovjhkek5m97n3y
        foreign key (role_id)
            references role;

alter table user_role
    add constraint FKg0dlvivcictt4d3df7hlbftu6
        foreign key (user_id)
            references ro_user;
