INSERT INTO department(department_id,department_name, department_tag) VALUES (1, 'Living Room', 'NF');

INSERT INTO department(department_id,department_name, department_tag) VALUES (2, 'Mattresses', 'NF');

INSERT INTO department(department_id,department_name, department_tag) VALUES (3,'Electronics', 'NF');


INSERT INTO manufacturer (manufacturer_id, created_timestamp, manufacturer_name, manufacturer_origin, years_warranty) VALUES (1,now(),'Purpple Matress', 'China', 1);

INSERT INTO  inventory (location,qty, unit_cost, manufacturer_id) VALUES("GS 101", 1200,600.00,1);
INSERT INTO  inventory (location,qty, unit_cost, manufacturer_id) VALUES("GS 102", 3200,400.00,1);
INSERT INTO product (product_id,product_name, product_price, description, dept_id, inventory_id) VALUES (1,'Purple Mattress- Queen', 1250.99,'Dimension 3.3 * 4.5 * 4.6',2,1);
INSERT INTO product(product_id,product_name, product_price, description, dept_id, inventory_id) VALUES (2,'Purple Mattress- king',     3300,'Dimension 5.3 * 6.5 * 4.6', 2,2);
INSERT INTO product(product_id, product_name, product_price, description, dept_id,inventory_id) VALUES (3,'Purple Mattress- Regular', 750.99 ,'dimension 3 * 3.3 * 4',2);

