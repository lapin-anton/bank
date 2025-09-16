
alter table public.account
    add version bigint default 0 not null;