INSERT INTO department
(department_id,department_name) VALUES (1,'Furniture');

INSERT INTO department
(department_id,department_name) VALUES (2,'Mattresses');

INSERT INTO department
(department_id,department_name) VALUES ('Electronics');

INSERT INTO manufacturer
(manufacturer_id,
created_timestamp,
manufacturer_name)
VALUES
(1,now(),'Purpple');

INSERT INTO product
(product_id,description,product_name,dept_id,manuf_id, qty)
VALUES (1,'size QUEEN','Purple Mattress- ',2, 1, 100);

INSERT INTO product
(product_id,description,product_name,dept_id,manuf_id, qty)
VALUES (2,'size King','Purple Mattress-', 2, 1, 400);

INSERT INTO product
(product_id,description,product_name,dept_id,manuf_id,qty)
VALUES (3,'size ','Purple Mattress- ', 2, 1, 200);

