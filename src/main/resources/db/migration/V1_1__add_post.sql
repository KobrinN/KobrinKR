create table if not exists public.post(
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    image varchar(255) NOT NULL,
    text varchar NOT NULL,
    user_id bigint NOT NULL,
    PRIMARY KEY (id)
);
alter table if exists public.post
    add constraint fk_user foreign key (user_id)
    references public.user (id) match simple
    on update CASCADE
    on delete CASCADE
    NOT VALID;