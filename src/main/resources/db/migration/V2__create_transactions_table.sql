CREATE TABLE transactions(
    id SERIAL NOT NULL,
    account_id VARCHAR(255) NOT NULL,
    amount FLOAT(15) NOT NULL,
    merchant VARCHAR(100) NOT NULL,
    mcc VARCHAR(5) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
)
