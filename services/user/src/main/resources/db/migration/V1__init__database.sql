CREATE SEQUENCE IF NOT EXISTS users_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_address_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS token_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS role_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS users_roles_seq START WITH 1 INCREMENT BY 1;

CREATE TYPE user_role_enum AS ENUM ('ADMIN', 'USER');

CREATE TABLE users (
    "id" integer PRIMARY KEY,
    "first_name" varchar NOT NULL,
    "last_name" varchar NOT NULL,
    "email" varchar UNIQUE NOT NULL,
    "password" varchar,
    "phone_number" varchar NOT NULL,
    "is_verified" boolean,
    "enabled" boolean,
    "created_at" TIMESTAMP WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE user_address (
    "id" integer PRIMARY KEY,
    "user_id" integer NOT NULL,
    "address_line" varchar,
    "city" varchar,
    "state" varchar,
    "country" varchar,
    "postal_code" varchar,
    "is_primary" boolean,
    "created_at" TIMESTAMP WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT FK_USER_ADDRESS FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

CREATE TABLE token (
    "id" integer PRIMARY KEY,
    "token" VARCHAR UNIQUE NOT NULL,
    "created_at" TIMESTAMP WITHOUT TIME ZONE,
    "expires_at" TIMESTAMP WITHOUT TIME ZONE,
    "validated_at" TIMESTAMP WITHOUT TIME ZONE,
    "user_id" INT NOT NULL,
    CONSTRAINT FK_USER_TOKEN FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

CREATE TABLE role (
    "id" integer PRIMARY KEY,
    "title" VARCHAR UNIQUE NOT NULL,
    "created_at" TIMESTAMP WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE users_roles (
    "id" integer PRIMARY KEY DEFAULT nextval('users_roles_seq'),
    "users_id" integer NOT NULL,
    "roles_id" integer NOT NULL,
    "created_at" TIMESTAMP WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT FK_USER_ROLE FOREIGN KEY ("users_id") REFERENCES "users" ("id") ON DELETE CASCADE,
    CONSTRAINT FK_ROLE_USER FOREIGN KEY ("roles_id") REFERENCES "role" ("id") ON DELETE CASCADE
);
