CREATE SEQUENCE IF NOT EXISTS shop_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE "shops" (
                         "id" integer PRIMARY KEY,
                         "owner_id" integer NOT NULL,
                         "name" varchar NOT NULL,
                         "description" text,
                         "address" varchar,
                         "enabled" boolean,
                         "rating" float,
                         "created_at" TIMESTAMP WITHOUT TIME ZONE,
                         "updated_at" TIMESTAMP WITHOUT TIME ZONE
);

ALTER TABLE products
    ADD COLUMN shop_id INTEGER REFERENCES shops(id);