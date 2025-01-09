ALTER TABLE "inventories"
    ADD CONSTRAINT unique_product_color UNIQUE ("product_id", "color_id");

-- Insert sample data into categories
INSERT INTO "categories" ("id", "name", "image", "enabled") VALUES
                                                                (nextval('category_seq'), 'Electronics', 'electronics.jpg', true),
                                                                (nextval('category_seq'), 'Home Appliances', 'home_appliances.jpg', true);

-- Insert sample data into sub_categories
INSERT INTO "sub_categories" ("id", "categories_id", "name") VALUES
                                                                 (nextval('sub_category_seq'), 1, 'Phones'),
                                                                 (nextval('sub_category_seq'), 1, 'Laptops'),
                                                                 (nextval('sub_category_seq'), 2, 'Refrigerators');

-- Insert sample data into products
INSERT INTO "products" (
    "id", "name", "short_description", "full_description", "main_image",
    "created_at", "updated_at", "brand", "sub_category_id", "average_rating", "review_count"
) VALUES
      (nextval('product_seq'), 'iPhone 14', 'Latest Apple smartphone', 'iPhone 14 with advanced features', 'iphone14.jpg',
       NOW(), NOW(), 'Apple', 1, 4.8, 1200),
      (nextval('product_seq'), 'Galaxy S23', 'Samsung flagship phone', 'Samsung Galaxy S23 with superior camera', 'galaxy_s23.jpg',
       NOW(), NOW(), 'Samsung', 1, 4.7, 950),
      (nextval('product_seq'), 'MacBook Air', 'Lightweight laptop', 'Apple MacBook Air with M2 chip', 'macbook_air.jpg',
       NOW(), NOW(), 'Apple', 2, 4.9, 600);

-- Insert sample data into colors
INSERT INTO "colors" ("id", "name") VALUES
                                        (nextval('color_seq'), 'Black'),
                                        (nextval('color_seq'), 'Silver'),
                                        (nextval('color_seq'), 'Blue');

-- Insert sample data into inventories
INSERT INTO "inventories" (
    "id", "product_id", "color_id", "available_quantity", "list_price", "discount_percent", "cost", "enabled"
) VALUES
      (nextval('inventory_seq'), 1, 1, 100, 999.99, 10.0, 850.00, 1),
      (nextval('inventory_seq'), 1, 2, 50, 999.99, 5.0, 850.00, 1),
      (nextval('inventory_seq'), 2, 3, 120, 799.99, 15.0, 700.00, 1),
      (nextval('inventory_seq'), 3, 2, 30, 1199.99, 0.0, 1100.00, 1);

-- Insert sample data into product_images
INSERT INTO "product_images" ("id", "name", "product_id") VALUES
                                                              (nextval('product_image_seq'), 'iphone14_front.jpg', 1),
                                                              (nextval('product_image_seq'), 'iphone14_back.jpg', 1),
                                                              (nextval('product_image_seq'), 'galaxy_s23_front.jpg', 2),
                                                              (nextval('product_image_seq'), 'macbook_air_open.jpg', 3);

-- Insert sample data into product_details
INSERT INTO "product_details" ("id", "name", "value", "product_id") VALUES
                                                                        (nextval('product_detail_seq'), 'Battery Life', '20 hours', 1),
                                                                        (nextval('product_detail_seq'), 'Display Size', '6.1 inches', 1),
                                                                        (nextval('product_detail_seq'), 'Processor', 'A16 Bionic', 1),
                                                                        (nextval('product_detail_seq'), 'Battery Life', '25 hours', 2),
                                                                        (nextval('product_detail_seq'), 'Processor', 'Snapdragon 8 Gen 2', 2),
                                                                        (nextval('product_detail_seq'), 'Weight', '2.7 lbs', 3),
                                                                        (nextval('product_detail_seq'), 'Storage', '512 GB SSD', 3);

