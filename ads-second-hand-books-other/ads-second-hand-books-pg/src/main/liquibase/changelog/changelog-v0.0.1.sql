--liquibase formatted sql

--changeset shemyakin:1 labels:v0.0.1
CREATE TYPE "ad_types_type" AS ENUM ('demand', 'proposal');

CREATE TABLE "ads" (
	"id" text primary key constraint ads_id_length_ctr check (length("id") < 64),
	"title" text constraint ads_title_length_ctr check (length(title) < 256),
	"authors" text constraint ads_authors_length_ctr check (length(title) < 256),
	"publishing" text constraint ads_publishing_length_ctr check (length(title) < 256),
	"year" integer not null,
	"ad_type" ad_types_type not null,
	"price" text constraint ads_price_length_ctr check (length(title) < 21),
	"owner_id" text not null constraint ads_owner_id_length_ctr check (length(id) < 64),
	"lock" text not null constraint ads_lock_length_ctr check (length(id) < 64)
);

CREATE INDEX ads_owner_id_idx on "ads" using hash ("owner_id");

CREATE INDEX ads_ad_type_idx on "ads" using hash ("ad_type");
