-- V3__create_income_table.sql
-- This migration is applied per tenant schema
CREATE TABLE incomes (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    amount DECIMAL(15, 2) NOT NULL,
    description TEXT,
    transaction_date DATE NOT NULL,
    category_id UUID,
    tenant_id UUID NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_incomes_tenant_id ON incomes(tenant_id);
CREATE INDEX idx_incomes_transaction_date ON incomes(transaction_date);
CREATE INDEX idx_incomes_category_id ON incomes(category_id);
