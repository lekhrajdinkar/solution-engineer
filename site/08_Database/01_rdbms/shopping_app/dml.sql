-- ======================== INSERT =========

INSERT INTO customer (name, email, address) VALUES
('John Doe', 'john.doe@example.com', '123 Elm Street'),
('Jane Smith', 'jane.smith@example.com', '456 Oak Avenue');


INSERT INTO product (productname, price) VALUES
('Laptop', 1200.00),
('Smartphone', 800.00),
('Headphones', 150.00);

INSERT INTO app_order (customer_id,orderdate) VALUES
(1, CURRENT_TIMESTAMP),
(2, CURRENT_TIMESTAMP);

INSERT INTO app_order_detail (order_id, productname, quantity, price) VALUES
(1, 'Laptop', 1, 1200.00),
(1, 'Headphones', 2, 300.00),
(2, 'Smartphone', 1, 800.00);

INSERT INTO cart (customer_id, productid, quantity) VALUES
(1, 1, 1), -- John adds Laptop to cart
(1, 3, 1), -- John adds Headphones to cart
(2, 2, 1); -- Jane adds Smartphone to cart

-- ======================== SELECT =========

select * from public.customer c ;
select * from public.app_order;
select * from public.app_order_detail aod ;
select * from public.cart c ;
