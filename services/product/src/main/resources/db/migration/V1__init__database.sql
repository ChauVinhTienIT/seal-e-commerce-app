CREATE SEQUENCE IF NOT EXISTS category_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS color_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS inventory_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_detail_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_image_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS product_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS sub_category_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE categories
(
    id      INTEGER NOT NULL,
    name    VARCHAR(255),
    image   VARCHAR(255),
    enabled BOOLEAN,
    CONSTRAINT pk_categories PRIMARY KEY (id)
);

CREATE TABLE colors
(
    id   INTEGER NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_colors PRIMARY KEY (id)
);

CREATE TABLE inventories
(
    id                 INTEGER NOT NULL,
    product_id         INTEGER,
    color_id           INTEGER,
    available_quantity INTEGER,
    list_price         DOUBLE PRECISION,
    discount_percent   DOUBLE PRECISION,
    cost               DOUBLE PRECISION,
    enabled            INTEGER,
    CONSTRAINT pk_inventories PRIMARY KEY (id)
);

CREATE TABLE product_details
(
    id         INTEGER NOT NULL,
    name       VARCHAR(255),
    value      VARCHAR(255),
    product_id INTEGER,
    CONSTRAINT pk_product_details PRIMARY KEY (id)
);

CREATE TABLE product_images
(
    id         INTEGER NOT NULL,
    name       VARCHAR(255),
    product_id INTEGER,
    CONSTRAINT pk_product_images PRIMARY KEY (id)
);

CREATE TABLE products
(
    id                INTEGER      NOT NULL,
    name              VARCHAR(255) NOT NULL,
    short_description VARCHAR(255) NOT NULL,
    full_description  VARCHAR(255),
    main_image        VARCHAR(255),
    created_at        TIMESTAMP WITHOUT TIME ZONE,
    updated_at        TIMESTAMP WITHOUT TIME ZONE,
    brand             VARCHAR(255),
    sub_category_id   INTEGER,
    average_rating    DOUBLE PRECISION,
    review_count      INTEGER,
    is_available      BOOLEAN DEFAULT FALSE,
    is_active         BOOLEAN DEFAULT TRUE,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE sub_categories
(
    id            INTEGER NOT NULL,
    categories_id INTEGER,
    name          TEXT,
    CONSTRAINT pk_sub_categories PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT uc_products_name UNIQUE (name);

ALTER TABLE inventories
    ADD CONSTRAINT FK_INVENTORIES_ON_COLOR FOREIGN KEY (color_id) REFERENCES colors (id);

ALTER TABLE inventories
    ADD CONSTRAINT FK_INVENTORIES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_SUB_CATEGORY FOREIGN KEY (sub_category_id) REFERENCES sub_categories (id);

ALTER TABLE product_details
    ADD CONSTRAINT FK_PRODUCT_DETAILS_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE product_images
    ADD CONSTRAINT FK_PRODUCT_IMAGES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE sub_categories
    ADD CONSTRAINT FK_SUB_CATEGORIES_ON_CATEGORIES FOREIGN KEY (categories_id) REFERENCES categories (id);

