create table if not exists public.user
(
    id bigserial NOT NULL,
    username varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    birthdate date,
    sex char,
    pathPhotoProfile varchar(255),
    PRIMARY KEY (id)
);
create table if not exists public.role
(
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);
create table if not exists public.user_role
(
    id bigserial NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id)
);
alter table if exists public.user_role
    add constraint fk_user foreign key (user_id)
    references public.user (id) match simple
    on update NO ACTION
    on delete CASCADE
    NOT VALID;
alter table if exists public.user_role
    add constraint fk_role foreign key (role_id)
    references public.role (id) match simple
    on update NO ACTION
    on delete CASCADE
    NOT VALID;
insert into public.role(name) values ('ROLE_USER');
insert into public.role(name) values ('ROLE_ADMIN');