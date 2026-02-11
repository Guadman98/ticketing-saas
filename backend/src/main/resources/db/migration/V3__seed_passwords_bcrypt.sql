-- BCrypt hash para password: dev
-- Generado con BCrypt (strength 10). Puedes regenerarlo luego, pero esto sirve para DEV.
UPDATE users
SET password_hash = '$2a$10$G5t1zJbZg6bQ9mYtX0c9eO7zqVtQXg1n7q7gq8d5b7zEwKq2r5QZK'
WHERE email = 'admin@dev.local';
