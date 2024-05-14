create table if not exists public.comment(
  id bigserial NOT NULL,
  user_id bigint NOT NULL ,
  exhibit_id bigint NOT NULL ,
  text varchar NOT NULL ,
    date timestamp NOT NULL,

  primary key (id)
);

ALTER TABLE IF EXISTS public.comment
    add constraint fk_user foreign key (user_id)
        references public.user (id) match simple
        on update CASCADE
        on delete CASCADE
        NOT VALID;

ALTER TABLE IF EXISTS public.comment
    add constraint fk_exhibit foreign key (exhibit_id)
        references public.exhibit (id) match simple
        on update CASCADE
        on delete CASCADE
        NOT VALID;