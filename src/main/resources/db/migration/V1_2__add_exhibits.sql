create table if not exists public.exhibit(
    id bigserial NOT NULL,
    name varchar(255) NOT NULL,
    description varchar(255) NOT NULL,
    image varchar(255),
    primary key (id)
);
alter table if exists public.post
    add exhibit_id bigint NOT NULL default 1;
alter table if exists public.post
    add constraint fk_exhibits foreign key (exhibit_id)
    references public.exhibit (id) match simple
    on update NO ACTION
    on delete CASCADE
    NOT VALID;
