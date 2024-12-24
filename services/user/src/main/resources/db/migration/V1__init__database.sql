CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS user_address_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS token_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS role_seq START WITH 1 INCREMENT BY 1;


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
                                  "user_id" integer NOT NULL,
                                  "address_line" varchar,
                                  "city" varchar,
                                  "state" varchar,
                                  "country" varchar,
                                  "postal_code" varchar,
                                  "is_primary" boolean,
                                  "created_at" TIMESTAMP WITHOUT TIME ZONE,
                                  "updated_at" TIMESTAMP WITHOUT TIME ZONE,
                                  CONSTRAINT FK_USER_ADDRESS FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE "tokens" (
    id integer PRIMARY KEY, -- Auto-generated ID
    token VARCHAR UNIQUE NOT NULL, -- Unique token
    created_at TIMESTAMP WITHOUT TIME ZONE, -- Timestamp for when the token is created
    expires_at TIMESTAMP WITHOUT TIME ZONE, -- Timestamp for when the token expires
    validated_at TIMESTAMP WITHOUT TIME ZONE, -- Timestamp for when the token is validated
    user_id INT NOT NULL, -- Foreign key to the User table
    CONSTRAINT FK_USER_TOKEN FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE "roles" (
    id integer PRIMARY KEY,
    title VARCHAR UNIQUE NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT FK_USER_TOKEN FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
CREATE TABLE "users_roles" (
    id integer PRIMARY KEY,
    user_id integer NOT NULL,
    role_id integer NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    "updated_at" TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT FK_USER_ROLE FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    CONSTRAINT FK_ROLE_USER FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);
