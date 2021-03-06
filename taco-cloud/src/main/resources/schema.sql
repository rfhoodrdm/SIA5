-- Define the schema for the Taco Cloud database.

-- Definitions of taco ingredients
CREATE TABLE IF NOT EXISTS Ingredient (
    id      VARCHAR(4) NOT NULL,
    name    VARCHAR(25) NOT NULL,
    type    VARCHAR(10) NOT NULL
);


-- Individual tacos stored here
CREATE TABLE IF NOT EXISTS Taco (
    id          IDENTITY,
    name        VARCHAR(50) NOT NULL,
    created_at   TIMESTAMP NOT NULL
);

-- List of ingredients associated with each individual taco.
CREATE TABLE IF NOT EXISTS Taco_Ingredients (
   taco_id        BIGINT NOT NULL,
   ingredients_id  VARCHAR(4) NOT NULL
);


-- add foreign keys between Taco_Ingredients <--> Taco 
-- and Taco_Ingredients <--> Ingredients
ALTER TABLE Taco_Ingredients ADD FOREIGN KEY (taco_id) REFERENCES Taco(id);
ALTER TABLE Taco_Ingredients ADD FOREIGN KEY (ingredients_id) REFERENCES Ingredient(id);


-- Stores orders for tacos.
CREATE TABLE IF NOT EXISTS Taco_Order (
    id              IDENTITY,
    delivery_name    VARCHAR(50) NOT NULL,
    delivery_street  VARCHAR(50) NOT NULL,
    delivery_city    VARCHAR(50) NOT NULL,
    delivery_state   VARCHAR(2) NOT NULL,
    delivery_zip     VARCHAR(10) NOT NULL,
    cc_number        VARCHAR(16) NOT NULL,
    cc_expiration    VARCHAR(5) NOT NULL, 
    cc_cvv           VARCHAR(3) NOT NULL,
    placed_at        TIMESTAMP NOT NULL,
    user_id          BIGINT NOT NULL
);



-- Store tacos that are associated with an order
CREATE TABLE IF NOT EXISTS Taco_Order_Tacos (
    order_id        BIGINT NOT NULL,
    tacos_id        BIGINT NOT NULL
);

-- add foreign keys between Taco_Order_Tacos <--> Taco 
-- and Taco_Order_Tacos <--> Order
ALTER TABLE Taco_Order_Tacos ADD FOREIGN KEY (tacos_id) REFERENCES Taco(id);
ALTER TABLE Taco_Order_Tacos ADD FOREIGN KEY (order_id) REFERENCES Taco_Order(id);


-- Create user table to store user credentials.
CREATE TABLE IF NOT EXISTS USER (
    id              IDENTITY,
    username        VARCHAR(50),
    password        VARCHAR(100),
    fullname        VARCHAR(50),
    street          VARCHAR(50),
    city            VARCHAR(50),
    state           VARCHAR(50),
    zip             VARCHAR(50),
    phone           VARCHAR(50)
);

-- add foreign-key relationship between Taco_Order <--> User
ALTER TABLE Taco_Order ADD FOREIGN KEY (user_id) REFERENCES USER(id);


--make a table to store the relationship between Users and Orders.
--CREATE TABLE IF NOT EXISTS Taco_Order_User (
--    order_id        BIGINT NOT NULL,
--    user_id         BIGINT NOT NULL
--);

--add foreign keys between Taco_Order_User <--> User 
--and Taco_Order_User <--> Taco_Order
--ALTER TABLE Taco_Order_User ADD FOREIGN KEY (order_id) REFERENCES Taco_Order(id);
--ALTER TABLE Taco_Order_User ADD FOREIGN KEY (user_id) REFERENCES USER(id);
