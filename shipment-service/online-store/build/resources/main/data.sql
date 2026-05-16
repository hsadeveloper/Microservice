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
(product_id,description,product_name,dept_id,manuf_id)
VALUES (1,'size QUEEN','Purple Mattress- ',2,1);

