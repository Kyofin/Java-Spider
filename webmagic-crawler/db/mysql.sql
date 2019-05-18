create table dataset.github_repo
(
  id bigint auto_increment
    primary key,
  repo_author varchar(500) null,
  repo_name varchar(500) null,
  repo_readme mediumtext null,
  url varchar(500) null
);

create table dataset.github_user
(
  id bigint auto_increment
    primary key,
  user_avatar varchar(500) null,
  url varchar(500) null,
  user_label varchar(500) null,
  user_mention varchar(500) null,
  user_name varchar(500) null,
  user_nick_name varchar(500) null,
  user_profile text null
);

create table dataset.job_info
(
  id bigint auto_increment
    primary key,
  company_addr varchar(500) null,
  company_info text null,
  company_name varchar(500) null,
  job_addr varchar(500) null,
  job_info text null,
  job_name varchar(500) null,
  salary_max int null,
  salary_min int null,
  time varchar(500) null,
  url varchar(500) null
);

