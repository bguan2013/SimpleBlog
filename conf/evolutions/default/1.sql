# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table blog (
  blog_title                varchar(255) not null,
  blog_post                 varchar(255),
  date                      varchar(255),
  user_name                 varchar(255),
  constraint pk_blog primary key (blog_title))
;

create table pass_confirm (
  old_password              varchar(255) not null,
  new_password              varchar(255),
  confirm_password          varchar(255),
  constraint pk_pass_confirm primary key (old_password))
;

create table rss (
  rss_title                 varchar(255) not null,
  rss_author                varchar(255),
  rss_description           varchar(255),
  rss_date                  varchar(255),
  constraint pk_rss primary key (rss_title))
;

create table user (
  user_name                 varchar(255) not null,
  password                  varchar(255),
  constraint pk_user primary key (user_name))
;

create sequence blog_seq;

create sequence pass_confirm_seq;

create sequence rss_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists blog;

drop table if exists pass_confirm;

drop table if exists rss;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists blog_seq;

drop sequence if exists pass_confirm_seq;

drop sequence if exists rss_seq;

drop sequence if exists user_seq;

