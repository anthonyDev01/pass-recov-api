ALTER TABLE user_password_reset MODIFY password_code VARCHAR(6);
ALTER TABLE user_password_reset MODIFY password_expiry DATETIME;