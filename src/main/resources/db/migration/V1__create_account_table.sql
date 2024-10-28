CREATE TABLE account(
    id SERIAL NOT NULL,
    food_balance float DEFAULT 0,
    meal_balance float DEFAULT 0,
    cash_balance float DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
)
