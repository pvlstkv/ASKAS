create table "action"
(
    "id"             bigserial not null,
    "action_date"    date,
    "description"    varchar(255),
    "title"          varchar(255),
    "action_type_id" int8      not null,
    primary key ("id")
);
create table "action_type"
(
    "id"   bigserial not null,
    "type" varchar(255),
    primary key ("id")
);