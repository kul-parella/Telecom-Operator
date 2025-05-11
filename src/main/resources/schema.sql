-- customers table
CREATE TABLE customers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL
);

-- phone_numbers table
CREATE TABLE phone_numbers (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  number VARCHAR(20) NOT NULL,
  activated BOOLEAN DEFAULT FALSE,
  customer_id BIGINT,
  CONSTRAINT fk_customer
    FOREIGN KEY(customer_id) REFERENCES customers(id)
);
