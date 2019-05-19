create table github_repo
(
  id bigserial not null
    constraint github_repo_pk
    primary key,
  repo_author varchar(500),
  repo_name varchar(500),
  url varchar(500),
  repo_readme text
);


create table github_user
(
  id bigserial not null
    constraint github_user_pk
    primary key,
  user_avatar varchar(500),
  url varchar(500),
  user_label varchar(500),
  user_mention varchar(500),
  user_nick_name varchar(500),
  user_profile text,
  user_name varchar(500)
);


create table job_info
(
  id bigserial not null
    constraint job_info_pkey
    primary key,
  company_addr varchar(500),
  company_info text,
  company_name varchar(500),
  job_addr varchar(500),
  job_info text,
  job_name varchar(500),
  salary_max integer,
  salary_min integer,
  time varchar(500),
  url varchar(500)
);


create table csdnblog
(
  key_id serial not null
    constraint csdnblog_pkey
    primary key,
  id integer,
  title varchar(255) ,
  date varchar(255) ,
  tags varchar(255),
  category varchar(255) ,
  view integer,
  comments integer,
  copyright integer
);




