CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_address_seq START WITH 1 INCREMENT BY 1;

CREATE TYPE user_role AS ENUM ('ADMIN', 'USER');

CREATE TABLE "users" (
                         "id" integer PRIMARY KEY,
                         "first_name" varchar NOT NULL,
                         "last_name" varchar NOT NULL,
                         "email" varchar UNIQUE NOT NULL,
                         "password" varchar,
                         "phone_number" varchar NOT NULL,
                         "role" user_role DEFAULT 'USER',
                         "is_verified" boolean,
                         "enabled" boolean,
                         "created_at" TIMESTAMP WITHOUT TIME ZONE,
                         "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE "user_addresses" (
                                  "id" integer PRIMARY KEY,
                                  "user_id" integer,
                                  "address_line" varchar,
                                  "city" varchar,
                                  "state" varchar,
                                  "country" varchar,
                                  "postal_code" varchar,
                                  "is_primary" boolean,
                                  "created_at" TIMESTAMP WITHOUT TIME ZONE,
                                  "updated_at" TIMESTAMP WITHOUT TIME ZONE
);