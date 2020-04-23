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
    createdAt   TIMESTAMP NOT NULL
);

-- List of ingredients associated with each individual taco.
CREATE TABLE IF NOT EXISTS Taco_Ingredients (
    taco        BIGINT NOT NULL,
    ingredient  VARCHAR(4) NOT NULL
);


-- add foreign keys between Taco_Ingredients <--> Taco 
-- and Taco_Ingredients <--> Ingredients
ALTER TABLE Taco_Ingredients ADD FOREIGN KEY (taco) REFERENCES Taco(id);
ALTER TABLE Taco_Ingredients ADD FOREIGN KEY (ingredient) REFERENCES Ingredient(id);


-- Stores orders for tacos.
CREATE TABLE IF NOT EXISTS Taco_Order (
    id              IDENTITY,
    deliveryName    VARCHAR(50) NOT NULL,
    deliveryStreet  VARCHAR(50) NOT NULL,
    deliveryCity    VARCHAR(50) NOT NULL,
    deliveryState   VARCHAR(2) NOT NULL,
    deliveryZip     VARCHAR(10) NOT NULL,
    ccNumber        VARCHAR(16) NOT NULL,
    ccExpiration    VARCHAR(5) NOT NULL, 
    ccCVV           VARCHAR(3) NOT NULL,
    placedAt        TIMESTAMP NOT NULL
);


-- Store tacos that are associated with an order
CREATE TABLE IF NOT EXISTS Taco_Order_Tacos (
    tacoOrder   BIGINT NOT NULL,
    taco        BIGINT NOT NULL
);

-- add foreign keys between Taco_Order_Tacos <--> Taco 
-- and Taco_Order_Tacos <--> Order
ALTER TABLE Taco_Order_Tacos ADD FOREIGN KEY (taco) REFERENCES Taco(id);
ALTER TABLE Taco_Order_Tacos ADD FOREIGN KEY (tacoOrder) REFERENCES Taco_Order(id);
