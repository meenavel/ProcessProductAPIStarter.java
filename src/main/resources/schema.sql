CREATE TABLE PRODUCT (
  product_Id          INTEGER PRIMARY KEY,
  product_Name       VARCHAR(50) NOT NULL,
  product_description       VARCHAR(50) NOT NULL,
  product_Amount            FLOAT NOT NULL,
  product_Sold_Count    INTEGER
);

create sequence hibernate_sequence;