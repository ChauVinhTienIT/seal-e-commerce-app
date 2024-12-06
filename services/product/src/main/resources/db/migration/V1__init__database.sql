CREATE TABLE if not exists "categories" (
                              "id" integer PRIMARY KEY,
                              "name" varchar,
                              "image" varchar,
                              "enabled" boolean
);

CREATE TABLE if not exists "sub_categories" (
                                  "id" integer PRIMARY KEY,
                                  "categories_id" int,
                                  "name" varchar
);

CREATE TABLE if not exists "products" (
                            "id" integer PRIMARY KEY,
                            "name" varchar,
                            "short_description" varchar,
                            "full_description" text,
                            "main_image" varchar,
                            "created_at" timestamp,
                            "updated_at" timestamp,
                            "brand" varchar,
                            "sub_category_id" int,
                            "average_rating" float,
                            "review_count" int
);

CREATE TABLE if not exists "colors" (
                          "id" integer PRIMARY KEY,
                          "name" varchar
);

CREATE TABLE if not exists "inventories" (
                               "id" integer PRIMARY KEY,
                               "product_id" int,
                               "color_id" int,
                               "available_quantity" integer,
                               "list_price" float,
                               "discount_percent" float,
                               "cost" float,
                               "enabled" integer
);

CREATE TABLE if not exists "product_images" (
                                  "id" integer PRIMARY KEY,
                                  "name" varchar,
                                  "product_id" int
);

CREATE TABLE if not exists "product_details" (
                                   "id" integer PRIMARY KEY,
                                   "name" varchar,
                                   "value" varchar,
                                   "product_id" int
);

ALTER TABLE "products" ADD FOREIGN KEY ("sub_category_id") REFERENCES "sub_categories" ("id");

ALTER TABLE "inventories" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "inventories" ADD FOREIGN KEY ("color_id") REFERENCES "colors" ("id");

ALTER TABLE "product_images" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "product_details" ADD FOREIGN KEY ("product_id") REFERENCES "products" ("id");

ALTER TABLE "sub_categories" ADD FOREIGN KEY ("categories_id") REFERENCES "categories" ("id");


create sequence if not exists category_seq increment by 1;
create sequence if not exists sub_category_seq increment by 1;
create sequence if not exists product_seq increment by 1;
create sequence if not exists color_seq increment by 1;
create sequence if not exists inventory_seq increment by 1;
create sequence if not exists product_image_seq increment by 1;
create sequence if not exists product_detail_seq increment by 1;
