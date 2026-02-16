CREATE TABLE IF NOT EXISTS suppliers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
    type VARCHAR(255) NOT NULL,
    price_per_kg NUMERIC(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS deliveries (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL REFERENCES suppliers(id),
    delivery_date TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS delivery_items (
    id BIGSERIAL PRIMARY KEY,
    delivery_id BIGINT NOT NULL REFERENCES deliveries(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    weight_kg NUMERIC(10, 3) NOT NULL,
    cost NUMERIC(10, 2) NOT NULL
);
