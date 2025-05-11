-- seed some customers
INSERT INTO customers (name) VALUES
  ('Ashly'),
  ('Benny'),
  ('charlie'),
  ('Danny'),
  ('Elve'),
  ('Fiano'),
  ('Greg'),
  ('Henry'),
  ('Ishi'),
  ('Jacky'),
  ('Kate');

-- seed phone numbers
INSERT INTO phone_numbers (number, activated, customer_id) VALUES
  ('0400123456', TRUE,  1),
  ('0400987654', FALSE, 1),
  ('0411222311', TRUE, 2),
  ('0422333422', FALSE,  2),
  ('0422333433', TRUE,  3),
  ('0422333444', FALSE,  3),
  ('0422333455', TRUE,  4),
  ('0422333466', FALSE,  4),
  ('0422333477', TRUE,  5),
  ('0422333488', FALSE,  5),
  ('0422333499', TRUE,  6),
  ('0422331010', FALSE,  6),
  ('0422331111', TRUE,  7),
  ('0422333121', FALSE,  7),
  ('0422331213', TRUE,  8),
  ('0422333999', FALSE,  8),
  ('0422333888', TRUE,  9),
  ('0422333777', FALSE,  9),
  ('0422333000', TRUE,  10),
  ('0422333222', FALSE,  10),
  ('0422333333', TRUE,  11),
  ('0422333000', FALSE,  11);

